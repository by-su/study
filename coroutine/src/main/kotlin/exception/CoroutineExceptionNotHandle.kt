package exception

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 예외 전파가 Coroutine2에서 Corotuine1으로 되었기 때문에 예외가 핸들링 된 것으로 보고 ExceptionHandler는 동작하지 않는다.
 */
fun main() = runBlocking<Unit> {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("[예외 발생] ${throwable}")
    }

    CoroutineScope(Dispatchers.IO).launch(CoroutineName("Coroutine1")) {
        launch(CoroutineName("Coroutine2") + exceptionHandler) {
            throw Exception("Coroutine2에 예외가 발생했습니다.")
        }
    }

    delay(1000L)
}