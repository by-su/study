import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): kotlin.Unit = runBlocking {
    val whileJob = launch(Dispatchers.Default) {
        while (true) {
            println("작업중")
            delay(1L)
        }
    }

    delay(100)
    whileJob.cancel()
}