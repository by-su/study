package com.rootbly.batchprac.writer

import com.rootbly.batchprac.dto.MemberDTO
import com.rootbly.batchprac.mapper.MemberMapper
import org.slf4j.LoggerFactory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import kotlin.jvm.java

@Component
class MemberItemWriter(
    private val memberMapper: MemberMapper
) : ItemWriter<MemberDTO> {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private var totalWrittenCount = 0

    override fun write(chunk: Chunk<out MemberDTO>) {
        val ids = chunk.items.map { item -> item.id }

        memberMapper.findAllByIds(ids).forEach {member ->
            logger.info("Writing ${member.id}")
            totalWrittenCount++
        }

    }
}