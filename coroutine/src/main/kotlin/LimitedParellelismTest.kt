import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 출력
 * [DefaultDispatcher-worker-2 이미지 처리 완료
 * [DefaultDispatcher-worker-1 이미지 처리 완료
 * [DefaultDispatcher-worker-2 이미지 처리 완료
 * [DefaultDispatcher-worker-1 이미지 처리 완료
 */
fun main() = runBlocking<Unit> {
    val imageProcessingDispatcher = Dispatchers.Default.limitedParallelism(2)
    repeat(100) {
        launch(imageProcessingDispatcher) {
            Thread.sleep(1000L)
            println("[${Thread.currentThread().name} 이미지 처리 완료")
        }
    }
}