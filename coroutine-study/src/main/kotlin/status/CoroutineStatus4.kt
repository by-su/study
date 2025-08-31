package status

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep

fun main() = runBlocking<Unit> {
    val job: Job = launch(Dispatchers.IO) {
        while(true) {
            sleep(100L)
            println("작업중")
//            yield()
        }
    }

    job.cancel()
    println(job)
    printJobState(job)
}