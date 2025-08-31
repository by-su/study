import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): kotlin.Unit = runBlocking {
    val whileJob = launch(Dispatchers.Default) {
        while (true) {
            println("작업중")
        }
    }

    /**
     *  while문을 통해서 스레드는 취소 시점을 확인하지 않고 계속 점유하고 있기 Delay()의 여부와 상관없이 종료되지 않는다.
     */
    delay(100)
    whileJob.cancel()
}