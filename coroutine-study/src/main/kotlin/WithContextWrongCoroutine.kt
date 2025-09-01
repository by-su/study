import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    val result = withContext(Dispatchers.IO) {
        delay(1000L)
        "결과값"
    }

    val result2 = withContext(Dispatchers.IO) {
        delay(1000L)
        "결과값2"
    }

    val results = listOf(result, result2)
    println("합쳐진 결과값: ${results.joinToString(", ")}")
}