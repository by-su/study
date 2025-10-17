package com.rootbly.batchprac.mapper

import com.rootbly.batchprac.domain.Member
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MemberMapper {

    fun findAllByIds(ids: List<Long>): List<Member>
    fun saveAll(items: List<Member>)
}