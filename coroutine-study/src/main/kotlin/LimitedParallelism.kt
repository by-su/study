import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield


fun main() = runBlocking<Unit> {
    val defaultDispatcherOne = Dispatchers.Default.limitedParallelism(2)
    val defaultDispatcherTwo = Dispatchers.Default.limitedParallelism(2)
    val defaultDispatcherThree = Dispatchers.Default.limitedParallelism(2)
    val defaultDispatcherFour = Dispatchers.Default.limitedParallelism(2)
    val defaultDispatcherSix = Dispatchers.Default.limitedParallelism(2)
    repeat(100) {
        launch(defaultDispatcherOne) {
            delay(1000)
            println("ONE !! >> [${Thread.currentThread().name}], Task : $it")
        }

        launch(defaultDispatcherTwo) {
            delay(1000)
            println("TWO >> [${Thread.currentThread().name}], Task : $it")
        }

        launch(defaultDispatcherThree) {
            delay(1000)
            println("THREE >> [${Thread.currentThread().name}], Task : $it")
        }

        launch(defaultDispatcherFour) {
            delay(100)
            println("FOUR >> [${Thread.currentThread().name}], Task : $it")
        }

        launch(defaultDispatcherSix) {
            delay(100)
            println("FIVE >> [${Thread.currentThread().name}], Task : $it")
        }

//        launch(Dispatchers.Default) {
//            Thread.sleep(1000L)
//            println("DEFAULT >> [${Thread.currentThread().name}], Task : $it")
//        }
    }
}