package com.rootbly.batchprac.shop.config

import com.rootbly.batchprac.shop.Shop
import com.rootbly.batchprac.shop.ShopRankSnapShot
import com.rootbly.batchprac.shop.processor.ShopRankProcessor
import org.mybatis.spring.batch.MyBatisCursorItemReader
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ShopRankConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val shopItemReader: MyBatisCursorItemReader<Shop>,
    private val shopRankProcessor: ShopRankProcessor,
    private val shopRankWriter: ItemWriter<ShopRankSnapShot>
) {

    @Bean
    fun shopRankUpdateJob(): Job {
        return JobBuilder("shopRankUpdateJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(shopRankUpdateStep())
            .listener(object : org.springframework.batch.core.JobExecutionListener {
                override fun beforeJob(jobExecution: org.springframework.batch.core.JobExecution) {
                    shopRankProcessor.reset()
                }
            })
            .build()
    }

    @Bean
    fun shopRankUpdateStep(): Step {
        return StepBuilder("shopRankUpdateStep", jobRepository)
            .chunk<Shop, ShopRankSnapShot>(1000, transactionManager)
            .reader(shopItemReader)
            .processor(shopRankProcessor)
            .writer(shopRankWriter)
            .build()
    }
}