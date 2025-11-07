package com.rootbly.s3.batch

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.rootbly.s3.AwsProperties
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import software.amazon.awssdk.services.s3.S3Client
import java.time.LocalDateTime

/**
 * S3 데이터를 읽어오는 Spring Batch 설정
 *
 * Spring Boot 3.x에서는 @EnableBatchProcessing을 사용하지 않음
 * (자동 설정이 배치 메타데이터 테이블을 자동으로 생성)
 *
 * 사용 예제:
 * - S3에서 GZIP 압축된 JSON 파일을 읽어옴
 * - 각 JSON 객체를 처리 (변환, 검증 등)
 * - 결과를 출력하거나 데이터베이스에 저장
 */
@Configuration
class S3DataBatchConfig(
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties,
    private val objectMapper: ObjectMapper
) {

    /**
     * Job 정의: s3DataProcessingJob
     * 하나 이상의 Step으로 구성됨
     */
    @Bean
    fun s3DataProcessingJob(
        jobRepository: JobRepository,
        processS3DataStep: Step
    ): Job {
        return JobBuilder("s3DataProcessingJob", jobRepository)
            .start(processS3DataStep)
            .build()
    }

    /**
     * Step 정의: S3 데이터를 읽고 처리하는 단계
     *
     * chunk(10): 한 번에 10개씩 읽어서 처리하고 커밋
     * - Reader: S3에서 데이터 읽기
     * - Processor: 데이터 변환/처리
     * - Writer: 결과 쓰기
     */
    @Bean
    fun processS3DataStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        s3JsonGzipItemReader: S3JsonGzipItemReader
    ): Step {
        return StepBuilder("processS3DataStep", jobRepository)
            .chunk<JsonNode, ProcessedData>(10, transactionManager)
            .reader(s3JsonGzipItemReader)
            .processor(jsonDataProcessor())
            .writer(processedDataWriter())
            .build()
    }

    /**
     * ItemReader: S3에서 GZIP 압축된 JSON 파일 읽기
     *
     * @StepScope: JobParameters를 사용하여 동적으로 startDate를 주입받음
     * 각 Job 실행마다 새로운 Reader 인스턴스가 생성됨
     */
    @Bean
    @StepScope
    fun s3JsonGzipItemReader(
        @Value("#{jobParameters['startDate']}") startDateString: String?
    ): S3JsonGzipItemReader {
        // basePrefix는 고정값
        val basePrefix = "82/ccx_exchange_auth_ccx_production/access_token_issues_replaced_id/"

        // startDate는 JobParameters에서 String으로 받아서 LocalDateTime으로 변환
        val actualStartDate = if (startDateString != null && startDateString.isNotBlank()) {
            LocalDateTime.parse(startDateString)
        } else {
            LocalDateTime.now().minusHours(2)
        }

        println("📋 ItemReader 생성: basePrefix='$basePrefix', startDate='$actualStartDate'")

        return S3JsonGzipItemReader(
            s3Client = s3Client,
            awsProperties = awsProperties,
            basePrefix = basePrefix,
            startDate = actualStartDate,
            objectMapper = objectMapper
        )
    }

    /**
     * ItemProcessor: JSON 데이터를 처리하여 원하는 형태로 변환
     *
     * 예시:
     * - 데이터 검증
     * - 필드 추출
     * - 데이터 변환
     */
    @Bean
    fun jsonDataProcessor(): ItemProcessor<JsonNode, ProcessedData> {
        return ItemProcessor { jsonNode ->
            // JSON에서 필요한 필드 추출
            val id = jsonNode.get("id")?.asLong() ?: 0L
            val memberId = jsonNode.get("member_id")?.asLong() ?: 0L
            val deletedAt = jsonNode.get("deleted_at")?.asText()
            val createdAt = jsonNode.get("created_at")?.asText() ?: ""

            // 필요한 경우 데이터 검증
            if (id == 0L) {
                // null을 반환하면 이 아이템은 건너뜀
                return@ItemProcessor null
            }

            // ProcessedData 객체로 변환
            ProcessedData(
                id = id,
                memberId = memberId,
                isDeleted = deletedAt != null && deletedAt != "null",
                createdAt = createdAt
            )
        }
    }

    /**
     * ItemWriter: 처리된 데이터를 출력하거나 저장
     *
     * 예시:
     * - 콘솔에 출력
     * - 데이터베이스에 저장
     * - 다른 S3 버킷에 업로드
     * - 외부 API 호출
     */
    @Bean
    fun processedDataWriter(): ItemWriter<ProcessedData> {
        return ItemWriter { items ->
            println("=== Chunk 처리 시작 (${items.size()}개 아이템) ===")
            items.forEach { data ->
                println("처리 완료: $data")

                // 여기서 실제 작업 수행
                // 예: repository.save(data)
                // 예: restClient.post(data)
            }
            println("=== Chunk 처리 완료 ===\n")
        }
    }
}

/**
 * 처리된 데이터 모델
 */
data class ProcessedData(
    val id: Long,
    val memberId: Long,
    val isDeleted: Boolean,
    val createdAt: String
)

