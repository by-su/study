import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

val singleThreadDispatcher: CoroutineDispatcher =
    newSingleThreadContext("singleThreadDispatcher")

fun main() = runBlocking<Unit> {
    launch(singleThreadDispatcher) {
        println("[${Thread.currentThread().name}] 실행")
    }

    launch(singleThreadDispatcher) {
        println("[${Thread.currentThread().name}] 실행")
    }
}

