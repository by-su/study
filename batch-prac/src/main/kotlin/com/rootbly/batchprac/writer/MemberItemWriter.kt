package com.rootbly.batchprac.writer

import com.rootbly.batchprac.dto.MemberDTO
import org.slf4j.LoggerFactory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class MemberItemWriter : ItemWriter<MemberDTO> {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private var totalWrittenCount = 0

    override fun write(chunk: Chunk<out MemberDTO?>) {
        chunk.items.forEach { member ->
            totalWrittenCount++

            // 콘솔에 출력
            logger.info("""
                [$totalWrittenCount] Member Written:
                  - ID: ${member!!.id}
                  - Name: ${member!!.name}
                  - Email: ${member!!.email}
                  - Age: ${member!!.age}
                  - Active: ${member!!.active}
            """.trimIndent())
        }

        logger.info("============ Chunk Write Complete ============")
        logger.info("Total written so far: $totalWrittenCount")
    }
}