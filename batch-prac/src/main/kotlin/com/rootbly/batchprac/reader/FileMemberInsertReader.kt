package com.rootbly.batchprac.reader

import com.fasterxml.jackson.databind.ObjectMapper
import com.rootbly.batchprac.dto.FileMemberDTO
import org.springframework.batch.item.json.JacksonJsonObjectReader
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FileMemberInsertReader(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun fileMemberReaderJ(): JsonItemReader<FileMemberDTO> {
        return JsonItemReaderBuilder<FileMemberDTO>()
            .name("fileMemberReader")
            .resource(ClassPathResource("fileMember.json"))
            .jsonObjectReader(JacksonJsonObjectReader(objectMapper, FileMemberDTO::class.java))
            .build()
    }
}