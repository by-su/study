package com.rootbly.batchprac

import com.rootbly.batchprac.dto.MemberDTO
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import kotlin.test.assertEquals

@SpringBootTest
@TestPropertySource(properties = ["spring.batch.job.enabled=false"])
class FileItemReaderTest {

    @Autowired
    private lateinit var memberItemReader: JsonItemReader<MemberDTO>

    @Test
    fun `JSON 파일 읽기 테스트`() {
        // given
        memberItemReader.open(ExecutionContext())

        // when - 첫 번째 레코드 읽기
        val member1 = memberItemReader.read()

        // then
        assertNotNull(member1)
        assertEquals(1L, member1?.id)
        assertEquals("가상멤버_1", member1?.name)
        assertEquals("member_1@sampledata.com", member1?.email)
        assertEquals(21, member1?.age)
        assertEquals(false, member1?.active)

        // cleanup
        memberItemReader.close()
    }

}