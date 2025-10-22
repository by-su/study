package com.rootbly.batchprac.member.writer

import com.rootbly.batchprac.member.domain.FileMember
import com.rootbly.batchprac.member.mapper.FileMemberMapper
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class FileMemberInsertWriter(
    private val fileMemberMapper: FileMemberMapper
) : ItemWriter<FileMember> {

    override fun write(chunk: Chunk<out FileMember>) {
        fileMemberMapper.saveAll(chunk.items)
    }
}