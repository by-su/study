package com.rootbly.batchprac.mapper

import com.rootbly.batchprac.domain.FileMember
import org.apache.ibatis.annotations.Mapper

@Mapper
interface FileMemberMapper {

    fun saveAll(items: List<(FileMember)>)
}