package com.rootbly.batchprac.member.config

import com.rootbly.batchprac.member.domain.FileMember
import com.rootbly.batchprac.member.dto.FileMemberDTO
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class FileMemberConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val memberItemReader: JsonItemReader<FileMemberDTO>,
    private val memberItemProcessor: ItemProcessor<FileMemberDTO, FileMember>,
    private val memberItemWriter: ItemWriter<FileMember>,
) {

    @Bean
    fun fileMemberInsertJob(): Job {
        return JobBuilder("fileMemberInsertJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(fileMemberInsertBatchStep())
            .build()
    }

    @Bean
    fun fileMemberInsertBatchStep(): Step {
        return StepBuilder("fileMemberInsertStep", jobRepository)
            .chunk<FileMemberDTO, FileMember>(10, transactionManager)
            .reader(memberItemReader)
            .processor(memberItemProcessor)
            .writer(memberItemWriter)
            .build()
    }
}