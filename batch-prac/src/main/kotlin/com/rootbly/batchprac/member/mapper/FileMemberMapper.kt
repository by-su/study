package com.rootbly.batchprac.member.mapper

import com.rootbly.batchprac.member.domain.FileMember
import org.apache.ibatis.annotations.Mapper

@Mapper
interface FileMemberMapper {

    fun saveAll(items: List<(FileMember)>)
    fun findLatestByMemberId(): List<FileMember>
}