package com.rootbly.batchprac.member.reader

import com.rootbly.batchprac.member.domain.FileMember
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.batch.MyBatisCursorItemReader
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder
import org.springframework.stereotype.Component

@Component
class MemberItemReader(
    private val sqlSessionFactory: SqlSessionFactory
) {

    fun reader(): MyBatisCursorItemReader<FileMember> {
        return MyBatisCursorItemReaderBuilder<FileMember>()
            .sqlSessionFactory(sqlSessionFactory)
            .queryId("com.rootbly.batchprac.member.mapper.FileMemberMapper.findLatestByMemberId")
            .build()
    }
}