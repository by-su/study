package com.rootbly.batchprac.domain

import java.time.LocalDateTime

data class Member(
    val id: Long,
    val memberId: Long,
    val joinedAt: LocalDateTime? = null,
    val joinCanceled: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
}