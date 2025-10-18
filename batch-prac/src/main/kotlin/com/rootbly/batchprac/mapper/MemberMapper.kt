package com.rootbly.batchprac.mapper

import com.rootbly.batchprac.domain.Member
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MemberMapper {

    fun saveAll(items: List<Member>)

    fun updateJoinCanceledBatch(items: List<Member>)
}