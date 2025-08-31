package status

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val job: Job = launch(Dispatchers.IO) {
        while(true) {
            yield()
        }
    }

    job.cancelAndJoin()
    println(job)
    printJobState(job)
}