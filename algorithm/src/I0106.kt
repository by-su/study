fun main() {
    val input = readLine()!!

    val set = mutableSetOf<Char>()
    val result = StringBuilder()

    for (c in input) {
        if (c !in set) {
            set.add(c)
            result.append(c)
        }
    }

    println(result.toString())

}