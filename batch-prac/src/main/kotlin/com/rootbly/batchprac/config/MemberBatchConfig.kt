package com.rootbly.batchprac.config

import com.rootbly.batchprac.dto.MemberDTO
import com.rootbly.batchprac.processor.MemberItemProcessor
import com.rootbly.batchprac.writer.MemberItemWriter
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class MemberBatchConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val memberItemReader: JsonItemReader<MemberDTO>,
    private val memberItemProcessor: MemberItemProcessor,
    private val memberItemWriter: MemberItemWriter,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun memberProcessingJob(): Job {
        return JobBuilder("memberProcessingJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(memberProcessingStep())  // Step 연결
            .build()
    }

    @Bean
    fun memberProcessingStep(): Step {
        return StepBuilder("memberProcessingStep", jobRepository)
            .chunk<MemberDTO, MemberDTO>(10, transactionManager)
            .reader(memberItemReader)
            .processor(memberItemProcessor)
            .writer(memberItemWriter)
            .faultTolerant()  // 에러 처리 활성화
            .retryLimit(3)  // 재시도 3회
            .retry(Exception::class.java)
            .build()
    }

}