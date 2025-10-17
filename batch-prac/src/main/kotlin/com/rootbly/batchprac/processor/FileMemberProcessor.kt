package com.rootbly.batchprac.processor

import com.rootbly.batchprac.domain.FileMember
import com.rootbly.batchprac.dto.MemberDTO
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class FileMemberProcessor(

) : ItemProcessor<MemberDTO, FileMember> {

    override fun process(item: MemberDTO): FileMember {
        return item.toFileMember()
    }
}