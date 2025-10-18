import com.rootbly.batchprac.domain.FileMember
import java.time.LocalDateTime
import kotlin.collections.forEachIndexed
import kotlin.random.Random

fun main() {
    val dummyData = generateFileMemberDummyData(200) // 50개의 더미 데이터 생성

    // JSON 형식으로 출력
    println("[")
    dummyData.forEachIndexed { index, fileMember ->
        println("  {")
        println("    \"id\": ${fileMember.id},")
        println("    \"memberId\": ${fileMember.memberId},")
        println("    \"deletedAt\": ${fileMember.deletedAt?.let { "\"$it\"" } ?: "null"},")
        println("    \"createdAt\": \"${fileMember.createdAt}\",")
        println("    \"updatedAt\": \"${fileMember.updatedAt}\"")
        print("  }")
        if (index < dummyData.size - 1) println(",") else println()
    }
    println("]")
}

fun generateFileMemberDummyData(count: Int): List<FileMember> {
    val now = LocalDateTime.now()
    val random = Random.Default

    return (1..count).map { index ->
        val createdAt = now.minusDays(random.nextLong(0, 365))
        val updatedAt = createdAt.plusDays(random.nextLong(0, 30))
        val deletedAt = if (random.nextDouble() < 0.3) { // 30% 확률로 삭제됨
            updatedAt.plusDays(random.nextLong(1, 10))
        } else {
            null
        }

        FileMember(
            id = index.toLong(),
            memberId = random.nextLong(1, 101), // 1 ~ 100
            deletedAt = deletedAt,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}