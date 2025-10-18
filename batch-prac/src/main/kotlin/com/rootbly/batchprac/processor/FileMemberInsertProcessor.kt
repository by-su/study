package com.rootbly.batchprac.processor

import com.rootbly.batchprac.domain.FileMember
import com.rootbly.batchprac.dto.FileMemberDTO
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class FileMemberInsertProcessor(

) : ItemProcessor<FileMemberDTO, FileMember> {

    override fun process(item: FileMemberDTO): FileMember {
        return item.toFileMember()
    }
}