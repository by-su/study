package com.rootbly.s3.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

/**
 * 배치 Job을 실행하는 컨트롤러
 */
@RestController
@RequestMapping("/api/batch")
class BatchController(
    private val jobLauncher: JobLauncher,
    private val s3DataProcessingJob: Job
) {

    /**
     * S3 데이터 처리 배치 실행
     *
     * 예시:
     * POST /api/batch/run-s3-data-processing
     * POST /api/batch/run-s3-data-processing?startDate=2025-11-06T20:00:00
     *
     * @param startDate 조회 시작 날짜 (기본값: 현재 시간 - 2시간)
     * @return 배치 실행 결과
     */
    @PostMapping("/run-s3-data-processing")
    fun runS3DataProcessing(
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        startDate: LocalDateTime?
    ): Map<String, Any?> {
        // 기본값 설정
        val actualStartDate = startDate ?: LocalDateTime.now().minusHours(2)

        // JobParameters: 각 실행을 구분하기 위한 파라미터
        // LocalDateTime은 String으로 변환하여 전달
        val jobParameters = JobParametersBuilder()
            .addLocalDateTime("runTime", LocalDateTime.now())
            .addString("startDate", actualStartDate.toString())
            .toJobParameters()

        return try {
            val jobExecution = jobLauncher.run(s3DataProcessingJob, jobParameters)

            mapOf(
                "success" to true,
                "jobId" to jobExecution.jobId,
                "status" to jobExecution.status.name,
                "startTime" to jobExecution.startTime,
                "endTime" to jobExecution.endTime,
                "exitCode" to jobExecution.exitStatus.exitCode,
                "startDate" to actualStartDate,
                "message" to "배치 작업이 시작되었습니다."
            )
        } catch (e: Exception) {
            mapOf(
                "success" to false,
                "error" to e.message,
                "errorType" to e.javaClass.simpleName
            )
        }
    }
}
