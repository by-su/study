package com.rootbly.batchprac.writer

import com.rootbly.batchprac.domain.Member
import com.rootbly.batchprac.mapper.MemberMapper
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class MemberWriter(
    private val memberMapper: MemberMapper
) : ItemWriter<Member> {
    override fun write(chunk: Chunk<out Member>) {
        val members = chunk.items.filterNotNull()

        if (members.isNotEmpty()) {
            memberMapper.updateJoinCanceledBatch(members)
            println("Updated ${members.size} members to join_canceled = true")
        }
    }
}