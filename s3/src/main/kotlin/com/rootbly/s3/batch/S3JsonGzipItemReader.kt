package com.rootbly.s3.batch

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.rootbly.s3.AwsProperties
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemStreamException
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader
import software.amazon.awssdk.core.ResponseInputStream
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import java.io.BufferedReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.GZIPInputStream

/**
 * S3에서 GZIP 압축된 JSON 파일을 읽어오는 Spring Batch ItemReader (스트리밍 방식)
 *
 * 특징:
 * - 날짜/시간 기반 필터링 지원 (processed_ts=... 폴더 형식)
 * - GZIP 압축 자동 해제
 * - 재시작 가능 (ExecutionContext를 통한 상태 관리)
 * - 스트리밍 방식으로 메모리 효율적 (파일 전체를 메모리에 로드하지 않음)
 *
 * @param s3Client AWS S3 클라이언트
 * @param awsProperties AWS 설정 (버킷 이름 등)
 * @param basePrefix S3 검색 시작 접두사 (예: "82/ccx_exchange_auth_ccx_production/")
 * @param startDate 조회 시작 날짜/시간 (이 시간 이후의 데이터만 읽음)
 * @param objectMapper JSON 파싱용 ObjectMapper
 */
open class S3JsonGzipItemReader(
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties,
    private val basePrefix: String,
    private val startDate: LocalDateTime,
    private val objectMapper: ObjectMapper = ObjectMapper()
) : AbstractItemCountingItemStreamItemReader<JsonNode>() {

    private lateinit var s3Objects: Iterator<String>

    // 스트리밍을 위한 필드들
    private var currentS3Stream: ResponseInputStream<GetObjectResponse>? = null
    private var currentGzipStream: GZIPInputStream? = null
    private var currentReader: BufferedReader? = null

    companion object {
        private val FOLDER_FORMATTER = DateTimeFormatter.ofPattern("'processed_ts='yyyy-MM-dd-HH-mm-ss")
    }

    init {
        // Reader 이름 설정 (재시작 시 상태를 구분하기 위해)
        setName("s3JsonGzipItemReader")
    }

    /**
     * Reader가 열릴 때 호출됨
     * S3에서 조건에 맞는 파일 목록을 가져옴
     */
    override fun doOpen() {
        println("=== S3JsonGzipItemReader 시작 ===")
        println("버킷: ${awsProperties.s3.bucketName}")
        println("Prefix: $basePrefix")
        println("시작 날짜: $startDate")

        // 1. 날짜/시간 조건에 맞는 폴더 목록 찾기
        val validFolderPrefixes = findValidFolders()
        println("발견된 폴더 수: ${validFolderPrefixes.size}")

        // 2. 각 폴더 내의 .json.gz 파일 목록 가져오기
        val allJsonGzFiles = validFolderPrefixes.flatMap { folderPrefix ->
            findJsonGzFiles(folderPrefix)
        }
        println("발견된 .json.gz 파일 수: ${allJsonGzFiles.size}")

        // 3. 파일 목록을 Iterator로 변환
        s3Objects = allJsonGzFiles.iterator()
    }

    /**
     * 다음 아이템을 읽어옴 (한 줄씩 스트리밍 방식)
     * 파일이 끝나면 다음 파일로 넘어감
     */
    override fun doRead(): JsonNode? {
        while (true) {
            // 현재 reader에서 한 줄 읽기
            val line = currentReader?.readLine()

            // 유효한 JSON 라인이면 파싱하여 반환
            if (line != null && line.isNotBlank()) {
                return try {
                    objectMapper.readTree(line)
                } catch (e: Exception) {
                    println("JSON 파싱 실패, 다음 줄로 건너뜀: $line")
                    continue
                }
            }

            // 현재 파일 끝 -> 스트림 정리
            closeCurrentFile()

            // 다음 파일이 없으면 종료
            if (!s3Objects.hasNext()) {
                return null
            }

            // 다음 파일 열기
            openNextFile(s3Objects.next())
        }
    }

    /**
     * Reader가 닫힐 때 호출됨
     */
    override fun doClose() {
        closeCurrentFile()
        println("=== S3JsonGzipItemReader 종료 ===")
    }

    /**
     * 다음 파일을 열고 스트림 초기화
     */
    private fun openNextFile(s3Key: String) {
        println("파일 스트리밍 시작: $s3Key")

        val getObjectRequest = GetObjectRequest.builder()
            .bucket(awsProperties.s3.bucketName)
            .key(s3Key)
            .build()

        // S3 스트림 열기
        currentS3Stream = s3Client.getObject(getObjectRequest)

        // GZIP 스트림 열기
        currentGzipStream = GZIPInputStream(currentS3Stream)

        // BufferedReader 생성
        currentReader = currentGzipStream?.bufferedReader(Charsets.UTF_8)
    }

    /**
     * 현재 파일의 모든 스트림 정리
     */
    private fun closeCurrentFile() {
        try {
            currentReader?.close()
            currentGzipStream?.close()
            currentS3Stream?.close()
        } catch (e: Exception) {
            println("스트림 종료 중 오류 발생: ${e.message}")
        } finally {
            currentReader = null
            currentGzipStream = null
            currentS3Stream = null
        }
    }

    /**
     * startDate 이후의 폴더 목록 찾기
     */
    private fun findValidFolders(): List<String> {
        val listFoldersRequest = ListObjectsV2Request.builder()
            .bucket(awsProperties.s3.bucketName)
            .prefix(basePrefix)
            .delimiter("/")
            .build()

        val allPrefixes = s3Client.listObjectsV2Paginator(listFoldersRequest)
            .commonPrefixes()
            .map { it.prefix() }
            .toList()

        println("🔍 전체 폴더 목록: $allPrefixes")

        return allPrefixes.mapNotNull { prefix ->
            val folderName = prefix.removePrefix(basePrefix).removeSuffix("/")
            println("📁 폴더 처리 중: prefix='$prefix', folderName='$folderName'")

            try {
                val folderDate = LocalDateTime.parse(folderName, FOLDER_FORMATTER)
                println("✅ 날짜 파싱 성공: $folderDate (startDate: $startDate)")

                if (!folderDate.isBefore(startDate)) {
                    println("✅ 날짜 조건 통과: $prefix")
                    prefix
                } else {
                    println("❌ 날짜 조건 불통과 (너무 오래됨): $prefix")
                    null
                }
            } catch (e: Exception) {
                println("❌ 날짜 파싱 실패: $folderName (오류: ${e.message})")
                null
            }
        }
    }

    /**
     * 특정 폴더 내의 .json.gz 파일 목록 찾기
     */
    private fun findJsonGzFiles(folderPrefix: String): List<String> {
        println("📂 폴더 내 파일 검색: $folderPrefix")

        val listFilesRequest = ListObjectsV2Request.builder()
            .bucket(awsProperties.s3.bucketName)
            .prefix(folderPrefix)
            .build()

        val files = s3Client.listObjectsV2Paginator(listFilesRequest)
            .contents()
            .filter { s3Object ->
                val isJsonGz = s3Object.key().endsWith(".json.gz") && s3Object.size() > 0
                if (isJsonGz) {
                    println("  ✅ 파일 발견: ${s3Object.key()} (${s3Object.size()} bytes)")
                }
                isJsonGz
            }
            .map { it.key() }
            .toList()

        println("📊 폴더 내 .json.gz 파일 총 ${files.size}개")
        return files
    }

}
