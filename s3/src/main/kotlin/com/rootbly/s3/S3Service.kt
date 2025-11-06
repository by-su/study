package com.rootbly.s3

import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.GZIPInputStream
import kotlin.random.Random


@Service
class S3Service(
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties
) {
    companion object {
        private const val PART_SIZE = 5 * 1024 * 1024 // 5MB (최소 파트 크기)
    }

    /**
     * S3에 파일 업로드 (단순 버전)
     */
    fun upload(data: ByteArray, key: String) {

        println("==== S3 Upload 시작 ====")
        println("🔍 버킷 이름: ${awsProperties.s3.bucketName}")
        println("🔍 리전: ${awsProperties.s3.region}")
        println("🔍 키(경로): $key")
        println("🔍 데이터 크기: ${data.size} bytes")
        println("========================")

        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(awsProperties.s3.bucketName)
                .key(key)
                .build(),
            RequestBody.fromBytes(data)
        )
    }

    /**
     * S3 키 생성: {prefix}/processed_ts={timestamp}/{seq}_part_00.json.gz
     * 시간은 2시간 간격 (00, 02, 04, ..., 22)
     */
    fun generateS3Key(
        prefix: String,
        sequenceNumber: Int,
        baseDateTime: LocalDateTime = LocalDateTime.now()
    ): String {
        val changedDate = baseDateTime.minusDays(1)
        val roundedHour = (baseDateTime.hour / 2) * 2
        val timestamp = changedDate
            .withHour(roundedHour)
            .withMinute(0)
            .withSecond(0)

        val ts = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
        val seq = sequenceNumber.toString().padStart(4, '0')
        return "$prefix/processed_ts=$ts/${seq}_part_00.json.gz"
    }

    fun generateManifestKey(
        prefix: String,
        baseDateTime: LocalDateTime = LocalDateTime.now()
    ): String {
        val changedDate = baseDateTime.minusDays(1)
        val roundedHour = (baseDateTime.hour / 2) * 2
        val timestamp = changedDate
            .withHour(roundedHour)
            .withMinute(0)
            .withSecond(0)

        val ts = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
        return "$prefix/processed_ts=$ts/manifest"
    }

    /**
     * 특정 날짜/시간 이후의 모든 S3 데이터를 읽어와 압축 해제 후 반환합니다.
     *
     * @param basePrefix 검색을 시작할 S3의 기본 접두사 (예: "data/logs/", 루트부터면 "")
     * @param startDate 이 시간 이후의 데이터를 조회합니다.
     * @return 압축 해제된 각 파일의 내용 (JSON 문자열) 리스트
     */
    fun readDataFrom(basePrefix: String, startDate: LocalDateTime): List<String> {
        // 1. 날짜/시간 포맷터 정의 (스크린샷의 'processed_ts=...' 형식 기준)
        val folderFormatter = DateTimeFormatter.ofPattern("'processed_ts='yyyy-MM-dd-HH-mm-ss")

        // 2. 날짜/시간 이후의 폴더(Prefix) 목록 찾기
        val listFoldersRequest = ListObjectsV2Request.builder()
            .bucket("roobly-test")
            .prefix(basePrefix)
            .delimiter("/") // 폴더 단위로 끊어서 조회
            .build()

        val validFolderPrefixes = s3Client.listObjectsV2Paginator(listFoldersRequest)
            .commonPrefixes() // "폴더" 목록 (예: "data/logs/processed_ts=.../")
            .mapNotNull { commonPrefix ->
                // "data/logs/processed_ts=..." -> "processed_ts=..."
                val folderName = commonPrefix.prefix().removePrefix(basePrefix).removeSuffix("/")
                try {
                    val folderDate = LocalDateTime.parse(folderName, folderFormatter)
                    // startDate 이후의 폴더만 선택
                    if (!folderDate.isBefore(startDate)) {
                        commonPrefix.prefix() // (예: "data/logs/processed_ts=.../")
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    // 날짜 파싱 실패 시 무시
                    null
                }
            }
            .toList() // 스트림을 리스트로 변환

        println(validFolderPrefixes)

        // 3. 각 폴더 내부의 .json.gz 파일 읽기
        return validFolderPrefixes.flatMap { folderPrefix ->
            readAndDecompressFiles(folderPrefix)
        }
    }

    /**
     * 특정 S3 폴더(Prefix) 내부의 모든 .json.gz 파일의 압축을 풀어 내용을 반환합니다.
     *
     * @param folderPrefix 파일을 읽어올 S3 폴더 경로
     * @return 압축 해제된 파일 내용(String) 리스트
     */
    private fun readAndDecompressFiles(folderPrefix: String): List<String> {
        val listFilesRequest = ListObjectsV2Request.builder()
            .bucket("roobly-test")
            .prefix(folderPrefix) // (예: "data/logs/processed_ts=.../")
            .build()

        val paginator = s3Client.listObjectsV2Paginator(listFilesRequest)

        return paginator.contents() // 폴더 내의 파일 목록
            .filter { s3Object ->
                // .json.gz 파일만 필터링 (스크린샷의 _manifest 등 제외)
                s3Object.key().endsWith(".json.gz") && s3Object.size() > 0
            }
            .map { s3Object ->
                // 4. S3에서 객체 가져오기
                val getObjectRequest = GetObjectRequest.builder()
                    .bucket("roobly-test")
                    .key(s3Object.key())
                    .build()

                println(getObjectRequest.key())

                // s3Client.getObject는 ResponseInputStream을 반환
                // use 블록을 사용하여 사용 후 자동으로 스트림을 닫음
                s3Client.getObject(getObjectRequest).use { s3Stream ->
                    // 5. GzipInputStream으로 압축 해제
                    GZIPInputStream(s3Stream).bufferedReader(Charsets.UTF_8).use { reader ->
                        reader.readText() // 파일 전체 내용을 문자열로 읽기
                    }
                }
            }
            .toList() // 스트림을 리스트로 변환
    }

}