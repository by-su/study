import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val parentJob = coroutineContext[Job]

    launch {
        val childJob = coroutineContext[Job]
        if (parentJob === childJob) {
            println("runBlocking으로 생성된 Job과 launch로 생성된 Job이 동일합니다.")
        } else {
            println("runBlocking으로 생성된 Job과 launch로 생성된 Job이 다릅니다.")
        }

        println("1. 자식 코루틴의 Job이 가지고 있는 parent는 부모 코루틴의 Job인가? ${childJob?.parent == parentJob}")
        println("2. 부모 코루틴의 Job은 자식 코루틴의 Job을 참조로 가지는가? ${parentJob?.children?.contains(childJob)}")
    }
}