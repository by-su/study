import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main(): kotlin.Unit = runBlocking {
    val tokenJob: Job = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 토큰 얻는 중")
        Thread.sleep(1000)
        println("[${Thread.currentThread().name}] 토큰 획득")
    }
    tokenJob.join()

    launch(Dispatchers.IO)  {
        println("[${Thread.currentThread().name}] API 호출")
    }

}