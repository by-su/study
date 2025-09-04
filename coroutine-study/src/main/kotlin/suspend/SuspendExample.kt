package suspend

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    delayAndPrintHelloWorld()
    delayAndPrintHelloWorld()
}

private suspend fun delayAndPrintHelloWorld() {
    delay(1000L)
    println("Hello, World!")
}