import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
    launch {
        while(true) {
            println("자식 코루틴에서 작업 실행 중")
        }
    }

    launch {
        while(true) {
            println("부모 코루틴에서 작업 실행 중")
        }
    }
}