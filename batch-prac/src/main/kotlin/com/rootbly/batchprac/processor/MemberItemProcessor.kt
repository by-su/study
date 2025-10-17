package com.rootbly.batchprac.processor

import com.rootbly.batchprac.domain.Member
import com.rootbly.batchprac.dto.MemberDTO
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class MemberItemProcessor : ItemProcessor<MemberDTO, Member> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun process(item: MemberDTO): Member {
        return item.toEntity()
    }
}