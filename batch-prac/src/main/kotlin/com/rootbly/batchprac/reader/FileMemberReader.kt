package com.rootbly.batchprac.reader

import com.fasterxml.jackson.databind.ObjectMapper
import com.rootbly.batchprac.dto.MemberDTO
import org.springframework.batch.item.json.JacksonJsonObjectReader
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FileMemberReader(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun fileMemberReaderJ(): JsonItemReader<MemberDTO> {
        return JsonItemReaderBuilder<MemberDTO>()
            .name("fileMemberReader")
            .resource(ClassPathResource("members.json"))
            .jsonObjectReader(JacksonJsonObjectReader(objectMapper, MemberDTO::class.java))
            .build()
    }
}