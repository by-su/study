package com.rootbly.batchprac.mapper

import com.rootbly.batchprac.domain.Member
import com.rootbly.batchprac.dto.MemberDTO
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MemberMapper {

    fun findAllByIds(ids: List<Long>): List<Member>
}