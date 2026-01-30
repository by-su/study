import java.util.Scanner

class I0111 {


}

fun main() {
    val `in` = Scanner(System.`in`)
    val input = `in`.next()

    var currentChar = input[0]
    var count = 1

    val sb = StringBuilder()
    for (i in 1..input.length - 1) {
        if (currentChar == input[i]) {
            count++
        } else {
            sb.append(currentChar)
            if (count > 1) sb.append(count)
            count = 1
            currentChar = input[i]
        }
    }

    sb.append(currentChar)
    if (count > 1) sb.append(count)

    println(sb)
}