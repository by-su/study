import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ConcurrentHashMap

fun main() = runBlocking {
    val defaultThreads = ConcurrentHashMap.newKeySet<Long>()
    val ioThreads = ConcurrentHashMap.newKeySet<Long>()

    println("=== Default 실행 ===")
    repeat(20) { i ->
        launch(Dispatchers.Default) {
            val threadId = Thread.currentThread().threadId()
            val threadName = Thread.currentThread().name
            defaultThreads.add(threadId)
            println("[Default-$i] ID: $threadId, Name: $threadName")
            Thread.sleep(1000)
        }
    }

    delay(2000) // Default 작업이 끝날 때까지 대기

    println("\n=== IO 실행 ===")
    repeat(20) { i ->
        launch(Dispatchers.IO) {
            val threadId = Thread.currentThread().id
            val threadName = Thread.currentThread().name
            ioThreads.add(threadId)
            println("[IO-$i] ID: $threadId, Name: $threadName")
            Thread.sleep(1000)
        }
    }

    delay(2000)

    println("\n=== 결과 분석 ===")
    println("Default에서 사용한 스레드 ID: ${defaultThreads.sorted()}")
    println("IO에서 사용한 스레드 ID: ${ioThreads.sorted()}")
    println("공통으로 사용한 스레드 ID: ${defaultThreads.intersect(ioThreads).sorted()}")
    println("공통 스레드 개수: ${defaultThreads.intersect(ioThreads).size}")
}