package status

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val job: Job = launch {
        delay(1000L)
    }

    job.join()
    println(job)
    printJobState(job)
}
