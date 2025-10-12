
fun main() {
    val printTimes = { text: String, times: Int ->
        for (i in 1..times) {
            print(text)
        }
    }

    printTimes("Hello", 5)
    printTimes("Na", 7)
}