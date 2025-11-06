package com.rootbly.s3

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import software.amazon.awssdk.services.s3.S3Client
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.util.zip.GZIPOutputStream

@RestController
@RequestMapping("/api/s3")
class S3Controller(
    private val s3Service: S3Service,
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties
) {

    /**
     * 설정 파일(AwsProperties)에 지정된 S3 버킷에 접근할 수 있는지 테스트
     * `headBucket` 요청을 사용하여 버킷의 존재 여부와 접근 권한을 확인
     *
     * @return 버킷 접근 성공 시 `{"success": true}`,
     * 실패 시 `{"success": false, "error": "에러 메시지"}`
     */
    @GetMapping("/test-bucket-access")
    fun testBucketAccess(): Map<String, Any?> {
        return try {
            s3Client.headBucket { it.bucket(awsProperties.s3.bucketName) }
            mapOf("success" to true)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message)
        }
    }

    /**
     * S3 API에 대한 기본 연결 및 인증을 테스트
     * `listBuckets` 요청을 사용하여 현재 자격증명으로 접근 가능한 모든 버킷 목록을 조회
     *
     * @return 연결 성공 시 `{"success": true, "buckets": ["bucket1", "bucket2", ...]}`,
     * 실패 시 `{"success": false, "error": "에러 메시지", "errorType": "에러 타입"}`
     */
    @GetMapping("/test-connection")
    fun testConnection(): Map<String, Any?> {
        return try {
            val response = s3Client.listBuckets()
            mapOf(
                "success" to true,
                "buckets" to response.buckets().map { it.name() }
            )
        } catch (e: Exception) {
            mapOf(
                "success" to false,
                "error" to e.message,
                "errorType" to e.javaClass.simpleName
            )
        }
    }

    /**
     * S3에 여러 개의 데이터 파일과 매니페스트 파일을 업로드하는 테스트 엔드포인트
     *
     * 단순 테스트 용도
     */
    @PostMapping("/upload")
    fun upload(): Map<String, Any> {
        val prefix = "82/ccx_exchange_auth_ccx_production/access_token_issues_replaced_id"
        val timestamp = LocalDateTime.now()

        // 데이터 파일들 업로드
        val uploadedFiles = (0..2).map { seq ->
            val key = s3Service.generateS3Key(prefix, seq, timestamp)

            // JSON 문자열 생성 (seq가 짝수면 deleted_at null, 홀수면 타임스탬프)
            val deletedAt = if (seq % 2 == 0) "null" else "\"2025-10-20 01:55:28.608227\""
            val jsonString = """{"id": $seq, "member_id": $seq, "deleted_at": $deletedAt, "created_at": "2025-10-20 01:55:28.608227"}"""

            // GZIP 압축
            val outputStream = ByteArrayOutputStream()
            GZIPOutputStream(outputStream).use { gzipStream ->
                gzipStream.write(jsonString.toByteArray(Charsets.UTF_8))
            }
            val compressedData = outputStream.toByteArray()

            s3Service.upload(compressedData, key)
            key
        }


        // manifest 업로드
        val manifestKey = s3Service.generateManifestKey(prefix, timestamp)
        val manifestData = uploadedFiles.joinToString("\n").toByteArray()
        s3Service.upload(manifestData, manifestKey)

        return mapOf(
            "uploaded" to uploadedFiles + manifestKey
        )
    }

    /**
     * 특정 날짜/시간 이후의 모든 S3 데이터를 읽어 반환합니다.
     *
     * @param startDate 조회 시작 날짜/시간 (ISO 8601 형식, 예: 2025-11-06T20:00:00)
     * @param basePrefix (선택) S3에서 검색을 시작할 기본 접두사 (예: "data/logs/")
     * @return 압축 해제된 파일 내용(JSON 문자열)의 리스트
     */
    @GetMapping("/read-data")
    fun readData(
        @RequestParam("startDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        startDate: LocalDateTime,

        @RequestParam("basePrefix", defaultValue = "") // 기본값은 버킷 루트
        basePrefix: String
    ): Map<String, Any?> {

        // basePrefix가 비어있지 않고 '/'로 끝나지 않으면 추가
        val formattedPrefix = if (basePrefix.isNotEmpty() && !basePrefix.endsWith("/")) {
            "$basePrefix/"
        } else {
            basePrefix
        }

        try {
            val data = s3Service.readDataFrom(formattedPrefix, startDate)
            return mapOf(
                "success" to true,
                "startDate" to startDate,
                "prefix" to formattedPrefix,
                "data" to data // 실제 데이터 (JSON 문자열 리스트)
            )
        } catch (e: Exception) {
            return mapOf(
                "success" to false,
                "error" to e.message,
                "errorType" to e.javaClass.simpleName
            )
        }
    }
}