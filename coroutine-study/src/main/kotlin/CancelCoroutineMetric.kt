import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    suspend fun testCancellationMethod(method: String, block: suspend () -> Unit) {
        val time = measureTimeMillis {
            val job = launch { block() }
            delay(50) // 50ms 후 취소
            job.cancelAndJoin()
        }
        println("$method: ${time}ms")
    }

    // 1. delay() 방식 - 지연 시간만큼 느림
    testCancellationMethod("delay(10) 방식") {
        repeat(100) { i ->
            delay(10) // 10ms씩 지연
        }
    }

    // 2. yield() 방식 - 스레드 양보
    testCancellationMethod("yield() 방식") {
        repeat(100000) { i ->
            yield() // 스레드 양보
        }
    }

    // 3. isActive 방식 - 양보 없음
    testCancellationMethod("isActive 방식") {
        repeat(100000) { i ->
            if (!this.isActive) return@repeat
            // CPU 작업 시뮬레이션
            var sum = 0
            repeat(100) { sum += it }
        }
    }
}