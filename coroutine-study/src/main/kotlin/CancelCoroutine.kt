import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): kotlin.Unit = runBlocking {
    val longJob = launch(Dispatchers.Default) {

        // delay()는 취소 가능한 중단 함수라서 cancelAndJoin()이 호출되면 즉시 CancellationException을 던지고 코루틴이 종료
        //  → println("취소되어야 할 JOB")까지 도달하지 않음.
        //
        // 반면 Thread.sleep()은 스레드를 블로킹할 뿐, 취소 신호를 확인하지 않음.
        // -> 코루틴은 취소 상태가 되었더라도 sleep()이 끝날 때까지 계속 진행

//        Thread.sleep(1000)
        delay(1000) // 코루틴만 중지
        println("취소되어야 할 JOB")
    }
//    longJob.cancel()
    longJob.cancelAndJoin()

    println("취소 후 동작해야 할 JOB")
}