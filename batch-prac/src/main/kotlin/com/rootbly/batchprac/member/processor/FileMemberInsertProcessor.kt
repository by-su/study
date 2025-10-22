package com.rootbly.batchprac.member.processor

import com.rootbly.batchprac.member.domain.FileMember
import com.rootbly.batchprac.member.dto.FileMemberDTO
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class FileMemberInsertProcessor(

) : ItemProcessor<FileMemberDTO, FileMember> {

    override fun process(item: FileMemberDTO): FileMember {
        return item.toFileMember()
    }
}