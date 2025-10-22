package com.rootbly.batchprac.member.domain

import java.time.LocalDateTime

data class FileMember(
    val id: Long,
    val memberId: Long,
    val deletedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
}