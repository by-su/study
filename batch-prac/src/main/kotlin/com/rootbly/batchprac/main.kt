import java.io.PrintWriter

fun main() {
    val totalCount = 100000 // 전체 데이터 건수
    val batchSize = 10000    // INSERT 문당 데이터 건수 (10,000건마다 새로운 INSERT 시작)
    val tableName = "members"
    val outputFileName = "insert_members_100k_single_file.sql"

    // SQL 파일을 생성하고 쓰기 위해 PrintWriter를 사용
    PrintWriter(outputFileName).use { writer ->
        var currentBatchStartId = 1

        // 10만 건의 데이터 생성
        for (i in 1..totalCount) {
            val id = i.toLong()
            val name = "User_$i"
            val email = "user$i@example.com"

            // 20세 ~ 59세 사이의 랜덤 나이
            val age = 20 + (i % 40)

            // 짝수 ID는 TRUE, 홀수 ID는 FALSE로 설정
            val active = (i % 2 == 0)

            // 10,000번째 데이터이거나 첫 번째 데이터일 때 새로운 INSERT 문 시작
            if ((i - 1) % batchSize == 0) {
                if (i > 1) {
                    writer.println(";") // 이전 INSERT 문 닫기
                    writer.println("\n-- --------------------------------------------------------------------------------")
                }
                currentBatchStartId = i
                writer.println("-- Batch ${((i - 1) / batchSize) + 1}: IDs from $currentBatchStartId to ${minOf(i + batchSize - 1, totalCount)}")
                writer.println("INSERT INTO $tableName (id, name, email, age, active) VALUES")
            }

            // VALUES 행 생성: (id, 'name', 'email', age, active)
            val row = "(${id}, '${name}', '${email}', ${age}, ${active})"

            // 현재 배치의 마지막 행인지 확인 (다음 배치가 시작되기 전)
            val isEndOfBatch = (i % batchSize == 0) || (i == totalCount)

            if (isEndOfBatch) {
                // 현재 INSERT 문은 닫지 않고, 다음 INSERT 문이 시작될 때 세미콜론을 찍음.
                // 마지막 데이터라면 여기서 끝내야 함.
                if (i == totalCount) {
                    writer.println("$row;")
                } else {
                    writer.println("$row") // 다음 줄에서 새로운 INSERT가 시작될 것이므로 콤마 없음
                }
            } else {
                writer.println("$row,") // 콤마로 다음 행 연결
            }
        }
    }

    println("✅ ${totalCount} 건의 데이터가 '${outputFileName}' 파일로 성공적으로 생성되었습니다.")
    println("하나의 파일이지만, 10,000건씩 10개의 INSERT 문으로 나뉘어 있어 대량 삽입에 용이합니다.")
}