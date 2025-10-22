package com.rootbly.batchprac.member.scheduler

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MemberBatchScheduler(
    private val jobLauncher: JobLauncher,
    private val memberUpdateJob: Job
) {

    /**
     * 매일 새벽 2시에 배치 실행
     */
    @Scheduled(cron = "0 0 2 * * *")
    fun runMemberUpdateJobDaily() {
        val jobParameters = JobParametersBuilder()
            .addString("datetime", LocalDateTime.now().toString())
            .toJobParameters()

        jobLauncher.run(memberUpdateJob, jobParameters)
    }

    /**
     * 매 10분마다 배치 실행
     */
    @Scheduled(cron = "0 */10 * * * *")
    fun runMemberUpdateJobEvery10Minutes() {
        val jobParameters = JobParametersBuilder()
            .addString("datetime", LocalDateTime.now().toString())
            .toJobParameters()

        jobLauncher.run(memberUpdateJob, jobParameters)
    }

    /**
     * 고정 간격으로 배치 실행 (60초마다)
     * fixedDelay: 이전 작업 완료 후 60초 대기
     */
    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    fun runMemberUpdateJobWithFixedDelay() {
        val jobParameters = JobParametersBuilder()
            .addString("datetime", LocalDateTime.now().toString())
            .toJobParameters()

        jobLauncher.run(memberUpdateJob, jobParameters)
    }
}