package com.rootbly.batchprac.shop.reader

import com.rootbly.batchprac.shop.Shop
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.batch.MyBatisCursorItemReader
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ShopRankReader(
    private val sqlSessionFactory: SqlSessionFactory
) {

    @Bean
    fun shopItemReader(): MyBatisCursorItemReader<Shop> {
        return MyBatisCursorItemReaderBuilder<Shop>()
            .sqlSessionFactory(sqlSessionFactory)
            .queryId("com.rootbly.batchprac.shop.mapper.ShopMapper.findAllOrderByTotalSalePriceDesc")
            .build()
    }
}