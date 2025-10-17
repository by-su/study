package com.rootbly.batchprac.writer

import com.rootbly.batchprac.domain.Member
import com.rootbly.batchprac.mapper.MemberMapper
import org.slf4j.LoggerFactory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import kotlin.jvm.java

@Component
class MemberItemWriter(
    private val memberMapper: MemberMapper
) : ItemWriter<Member> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun write(chunk: Chunk<out Member>) {
        memberMapper.saveAll(chunk.items)
    }
}