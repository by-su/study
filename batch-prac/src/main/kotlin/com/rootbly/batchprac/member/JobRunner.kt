//package com.rootbly.batchprac.member
//
//import org.slf4j.LoggerFactory
//import org.springframework.batch.core.Job
//import org.springframework.batch.core.JobParametersBuilder
//import org.springframework.batch.core.launch.JobLauncher
//import org.springframework.boot.CommandLineRunner
//import org.springframework.stereotype.Component
//import java.time.LocalDateTime
//
//@Component
//class JobRunner(
//    private val jobLauncher: JobLauncher,
//    private val fileMemberInsertJob: Job
//) : CommandLineRunner {
//
//    private val logger = LoggerFactory.getLogger(this::class.java)
//
//    override fun run(vararg args: String?) {
//        logger.info("Starting Batch Job Runner...")
//
//        val jobParameters = JobParametersBuilder()
//            .addString("inputFile", "member.json")
//            .addLocalDateTime("runTime", LocalDateTime.now())
//            .toJobParameters()
//
//        try {
//            val execution = jobLauncher.run(fileMemberInsertJob, jobParameters)
//            logger.info("Job Execution ID: ${execution.id}")
//            logger.info("Job Status: ${execution.status}")
//        } catch (e: Exception) {
//            logger.error("Failed to execute job", e)
//            throw e
//        }
//    }
//
////    override fun run(vararg args: String?) {
////        logger.info("Starting Batch Job Runner...")
////
////        val jobParameters = JobParametersBuilder()
////            .addString("inputFile", "member.json")
////            .addLocalDateTime("runTime", LocalDateTime.now())
////            .toJobParameters()
////
////        try {
////            val execution = jobLauncher.run(memberProcessingJob, jobParameters)
////            logger.info("Job Execution ID: ${execution.id}")
////            logger.info("Job Status: ${execution.status}")
////        } catch (e: Exception) {
////            logger.error("Failed to execute job", e)
////            throw e
////        }
////    }
//}