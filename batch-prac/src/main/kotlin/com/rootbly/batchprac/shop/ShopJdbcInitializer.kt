package com.rootbly.batchprac.shop

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.PreparedStatement
import java.util.*

@Component
class ShopJdbcInitializer(private val jdbcTemplate: JdbcTemplate) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {

        try {
            // 기존 데이터 확인
            val existingCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM shop", Int::class.java) ?: 0

            // 테이블 생성 쿼리
            val createTableQuery = """
                CREATE TABLE IF NOT EXISTS shop (
                    id BIGINT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    total_sale_price BIGINT NOT NULL
                )
            """.trimIndent()
            jdbcTemplate.execute(createTableQuery)

            val dummyShops = (1..300_000).map { i ->
                Shop(
                    id = i.toLong(),
                    name = "Shop_$i",
                    totalSalePrice = Random().nextLong(1000, 10000) // 판매 금액 (1000~10000 사이)
                )
            }

            // Batch Insert 쿼리
            val insertQuery = "INSERT INTO shop (id, name, total_sale_price) VALUES (?, ?, ?)"

            val updateCounts = jdbcTemplate.batchUpdate(insertQuery, object : BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    val shop = dummyShops[i]
                    ps.setLong(1, shop.id)
                    ps.setString(2, shop.name)
                    ps.setLong(3, shop.totalSalePrice)
                }

                override fun getBatchSize(): Int {
                    return dummyShops.size
                }
            })


        } catch (e: Exception) {
            println("ERROR during shop data insert: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

}
