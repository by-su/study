import kotlinx.coroutines.*

fun main() = runBlocking {
    println("=== 시작 ===\n")

    val parentJob = launch {
        println("1. 부모 코루틴 시작")

        // 자식 코루틴 1
        launch {
            println("  2. 자식1 시작")
            delay(2000)
            println("  5. 자식1 완료")
        }

        // 자식 코루틴 2
        launch {
            println("  3. 자식2 시작")
            delay(3000)
            println("  6. 자식2 완료")
        }

        println("4. 부모 본문 완료 (여기서 COMPLETING 상태 진입)")
        // ← 이 시점에 부모는 COMPLETING 상태
        // 자식들이 끝날 때까지 기다림
    }

    // 부모 본문이 끝난 직후
    delay(100)
    println("\n=== 부모 본문 종료 직후 (COMPLETING) ===")
    println("isActive: ${parentJob.isActive}")       // true ✅
    println("isCompleted: ${parentJob.isCompleted}") // false

    // 자식들이 모두 끝나길 기다림
    parentJob.join()

    println("\n=== 모든 자식 완료 후 (COMPLETED) ===")
    println("isActive: ${parentJob.isActive}")       // false
    println("isCompleted: ${parentJob.isCompleted}") // true ✅
}