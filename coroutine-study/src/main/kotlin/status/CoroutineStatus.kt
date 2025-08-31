package status

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val job: Job = launch(start = CoroutineStart.LAZY) {
        delay(1000L)
    }

    println(job)
    printJobState(job)
}

fun printJobState(job: Job) {
    println(
        "job.isActive: ${job.isActive}\n" +
        "job.isCompleted: ${job.isCompleted}\n" +
        "job.isCancelled: ${job.isCancelled}"
    )
}