import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException


fun main() = runBlocking {
    // 1. New 상태 - LAZY로 시작
    val lazyJob = launch(start = CoroutineStart.LAZY) {
        println("Lazy 코루틴 시작!")
        delay(1000)
    }
    println("Job 상태: ${lazyJob.isActive}") // false

    // 2. Active 상태 - 시작됨
    lazyJob.start()
    println("Job 상태: ${lazyJob.isActive}") // true

    // 3. 일반적인 코루틴 생성
    val job = launch {
        println("작업 시작")
        try {
            repeat(5) { i ->
                println("작업 중... $i")
                delay(500)
            }
        } catch (e: CancellationException) {
            println("취소되었습니다!")
            throw e // 중요: 다시 던져야 함
        } finally {
            println("정리 작업 실행")
        }
    }

    delay(1200)

    // 4. Cancelling 상태 - 취소 요청
    job.cancel()
    println("취소 요청함")

    // 5. Cancelled 상태 - 완전히 취소됨
    job.join() // 완전히 끝날 때까지 대기
    println("Job 완료: ${job.isCompleted}") // true
    println("Job 취소됨: ${job.isCancelled}") // true
}