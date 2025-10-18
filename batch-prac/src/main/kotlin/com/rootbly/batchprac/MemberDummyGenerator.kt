package com.rootbly.batchprac

import com.rootbly.batchprac.domain.Member
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun main() {
    val dummyData = generateMemberDummyData(200) // 200개의 더미 데이터 생성
    val sqlStatements = generateSqlInserts(dummyData)

    // SQL 파일 내용 출력
    println("-- Member 테이블 더미 데이터")
    println("-- Generated at: ${LocalDateTime.now()}")
    println()
    sqlStatements.forEach { println(it) }
}

fun generateMemberDummyData(count: Int): List<Member> {
    val now = LocalDateTime.now()
    val random = Random.Default

    return (1..count).map { index ->
        val createdAt = now.minusDays(random.nextLong(0, 365))
        val updatedAt = createdAt.plusDays(random.nextLong(0, 30))

        // 70% 확률로 joinedAt 설정
        val joinedAt = if (random.nextDouble() < 0.7) {
            createdAt.plusDays(random.nextLong(1, 7))
        } else {
            null
        }

        // 20% 확률로 joinCanceled true
        val joinCanceled = random.nextDouble() < 0.2

        Member(
            id = index.toLong(),
            memberId = index.toLong(),
            joinedAt = joinedAt,
            joinCanceled = joinCanceled,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}

fun generateSqlInserts(members: List<Member>): List<String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val tableName = "member"

    val statements = mutableListOf<String>()

    // 테이블 생성문 (옵션)
    statements.add("""
        CREATE TABLE IF NOT EXISTS $tableName (
            id BIGINT PRIMARY KEY,
            member_id BIGINT NOT NULL,
            joined_at TIMESTAMP,
            join_canceled BOOLEAN NOT NULL,
            created_at TIMESTAMP NOT NULL,
            updated_at TIMESTAMP NOT NULL
        );
    """.trimIndent())
    statements.add("")

    // INSERT 문 생성
    statements.add("INSERT INTO $tableName (id, member_id, joined_at, join_canceled, created_at, updated_at) VALUES")

    members.forEachIndexed { index, member ->
        val joinedAtValue = member.joinedAt?.let { "'${it.format(formatter)}'" } ?: "NULL"
        val values = """(${member.id}, ${member.memberId}, $joinedAtValue, ${member.joinCanceled}, '${member.createdAt.format(formatter)}', '${member.updatedAt.format(formatter)}')"""

        if (index < members.size - 1) {
            statements.add("    $values,")
        } else {
            statements.add("    $values;")
        }
    }

    return statements
}

// 대량 INSERT를 위한 배치 처리 버전
fun generateBatchSqlInserts(members: List<Member>, batchSize: Int = 50): List<String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val tableName = "member"
    val statements = mutableListOf<String>()

    statements.add("""
        CREATE TABLE IF NOT EXISTS $tableName (
            id BIGINT PRIMARY KEY,
            member_id BIGINT NOT NULL,
            joined_at TIMESTAMP,
            join_canceled BOOLEAN NOT NULL,
            created_at TIMESTAMP NOT NULL,
            updated_at TIMESTAMP NOT NULL
        );
    """.trimIndent())
    statements.add("")

    members.chunked(batchSize).forEach { batch ->
        statements.add("INSERT INTO $tableName (id, member_id, joined_at, join_canceled, created_at, updated_at) VALUES")

        batch.forEachIndexed { index, member ->
            val joinedAtValue = member.joinedAt?.let { "'${it.format(formatter)}'" } ?: "NULL"
            val values = """(${member.id}, ${member.memberId}, $joinedAtValue, ${member.joinCanceled}, '${member.createdAt.format(formatter)}', '${member.updatedAt.format(formatter)}')"""

            if (index < batch.size - 1) {
                statements.add("    $values,")
            } else {
                statements.add("    $values;")
            }
        }
        statements.add("")
    }

    return statements
}

// 파일로 저장하는 함수
fun saveSqlToFile(filename: String = "member_dummy_data.sql") {
    val dummyData = generateMemberDummyData(200)
    val sqlStatements = generateBatchSqlInserts(dummyData)

    java.io.File(filename).writeText(sqlStatements.joinToString("\n"))
    println("SQL 파일이 $filename 으로 저장되었습니다.")
}