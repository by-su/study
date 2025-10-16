package com.rootbly.batchprac.reader

import com.fasterxml.jackson.databind.ObjectMapper
import com.rootbly.batchprac.dto.MemberDTO
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder
import org.springframework.batch.item.database.support.H2PagingQueryProvider
import org.springframework.batch.item.json.JacksonJsonObjectReader
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.RowMapper
import javax.sql.DataSource

@Configuration
class MemberItemReaderConfig(
    private val objectMapper: ObjectMapper,
) {

    @Bean
    fun memberItemReader(): JsonItemReader<MemberDTO> {
        return JsonItemReaderBuilder<MemberDTO>()
            .name("memberItemReader")
            .resource(ClassPathResource("members.json"))
            .jsonObjectReader(JacksonJsonObjectReader(objectMapper, MemberDTO::class.java))
            .build()
    }

    @Bean
    fun userDatabaseReader(dataSource: DataSource): JdbcPagingItemReader<Long> {
        val queryProvider = H2PagingQueryProvider().apply {
            setSelectClause("SELECT user_id")
            setFromClause("FROM users")
            setSortKeys(mapOf("id" to Order.ASCENDING))
        }

        return JdbcPagingItemReaderBuilder<Long>()
            .name("userDatabaseReader")
            .dataSource(dataSource)
            .queryProvider(queryProvider)
            .pageSize(1000)  // 한 번에 1000개씩 읽기
            .rowMapper(RowMapper { rs, _ ->
                rs.getLong("user_id")
            })
            .build()
    }
}