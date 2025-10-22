package com.rootbly.batchprac.member.writer

import com.rootbly.batchprac.member.domain.Member
import com.rootbly.batchprac.member.mapper.MemberMapper
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