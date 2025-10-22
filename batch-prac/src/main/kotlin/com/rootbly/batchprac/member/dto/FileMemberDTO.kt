package com.rootbly.batchprac.member.dto

import com.rootbly.batchprac.member.domain.FileMember
import java.time.LocalDateTime

data class FileMemberDTO(
    val id: Long,
    val memberId: Long,
    val deletedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    fun toFileMember(): FileMember = FileMember(
        id = id,
        memberId = memberId,
        deletedAt = deletedAt,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
