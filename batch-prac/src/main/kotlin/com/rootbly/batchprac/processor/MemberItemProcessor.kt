package com.rootbly.batchprac.processor

import com.rootbly.batchprac.dto.MemberDTO
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class MemberItemProcessor : ItemProcessor<MemberDTO, MemberDTO> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun process(item: MemberDTO): MemberDTO {
        logger.info("Processing member: $item")
        return item
    }
}