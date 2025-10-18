package com.rootbly.batchprac.config

import com.rootbly.batchprac.domain.FileMember
import com.rootbly.batchprac.domain.Member
import com.rootbly.batchprac.reader.MemberItemReader
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class MemberConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val memberItemReader: MemberItemReader,
    private val memberProcessor: ItemProcessor<FileMember, Member>,
    private val memberWriter: ItemWriter<Member>,
) {

    @Bean
    fun memberUpdateJob(): Job {
        return JobBuilder("memberUpdateJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(memberUpdateStep())
            .build()
    }

    @Bean
    fun memberUpdateStep(): Step {
        return StepBuilder("memberUpdateStep", jobRepository)
            .chunk<FileMember, Member>(10, transactionManager)
            .reader(memberItemReader.reader())
            .processor(memberProcessor)
            .writer(memberWriter)
            .build()
    }

}