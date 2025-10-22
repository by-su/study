package com.rootbly.batchprac.member.mapper

import com.rootbly.batchprac.member.domain.Member
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MemberMapper {

    fun saveAll(items: List<Member>)

    fun updateJoinCanceledBatch(items: List<Member>)
}