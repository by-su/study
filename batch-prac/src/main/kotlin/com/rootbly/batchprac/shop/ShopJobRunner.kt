package com.rootbly.batchprac.shop

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ShopJobRunner(
    private val jobLauncher: JobLauncher,
    private val shopRankUpdateJob: Job
) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun run(vararg args: String?) {
        Thread.sleep(5000)
        logger.info("Starting Shop Rank Update Batch Job...")

        val jobParameters = JobParametersBuilder()
            .addLocalDateTime("runTime", LocalDateTime.now())
            .toJobParameters()

        try {
            val execution = jobLauncher.run(shopRankUpdateJob, jobParameters)
            logger.info("Shop Rank Job Execution ID: ${execution.id}")
            logger.info("Shop Rank Job Status: ${execution.status}")
        } catch (e: Exception) {
            logger.error("Failed to execute shop rank update job", e)
            throw e
        }
    }
}