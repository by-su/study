import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val parentJob = launch(Dispatchers.IO) {
        val dbResultsDeferred: List<Deferred<String>> = listOf("db1","db2","db3").map {
            async {
                delay(1000L)
                println("${it}로부터 데이터를 가져오는데 성공했습니다.")
                return@async "[${it}]data"
            }
        }

        val dbResults: List<String> = dbResultsDeferred.awaitAll()

        println(dbResults)
    }

    parentJob.cancel()
}