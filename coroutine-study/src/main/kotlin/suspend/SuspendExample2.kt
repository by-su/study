package suspend

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job1 = launch {
        delayAndPrintHelloWorld()
    }

    val job2 = launch {
        delayAndPrintHelloWorld()
    }

    joinAll(job1, job2)
    val totalTime = System.currentTimeMillis() - startTime
    println("Total Time: $totalTime ms")
}

private suspend fun delayAndPrintHelloWorld() {
    delay(1000L)
    println("Hello, World!")
}