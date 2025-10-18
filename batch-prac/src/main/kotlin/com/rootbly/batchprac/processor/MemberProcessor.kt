package com.rootbly.batchprac.processor

import com.rootbly.batchprac.domain.FileMember
import com.rootbly.batchprac.domain.Member
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MemberProcessor : ItemProcessor<FileMember, Member> {

    override fun process(fileMember: FileMember): Member? {
        // FileMember의 deletedAt이 null이면 처리하지 않음
        if (fileMember.deletedAt == null) {
            return null
        }

        // deletedAt이 있는 경우에만 Member 객체 생성 (업데이트 대상)
        return Member(
            id = 0L, // 업데이트시 실제 ID는 member_id로 찾음
            memberId = fileMember.memberId,
            joinedAt = null,
            joinCanceled = true, // deletedAt이 있으므로 join_canceled = true
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}