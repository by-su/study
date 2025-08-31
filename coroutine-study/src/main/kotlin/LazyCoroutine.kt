import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main(): kotlin.Unit = runBlocking {
    val lazyCoroutine = launch(start = CoroutineStart.LAZY) {
        println("[지연 코루틴 실행됨]")
    }

//    lazyCoroutine.start()
//    lazyCoroutine.join()

    println("메인 코루틴 END라인 도착")
}