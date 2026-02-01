import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)
    val l = sc.nextInt()
    val k = sc.nextInt()
    val arr = IntArray(l)
    for (i in 0 until l) {
        arr[i] = sc.nextInt()
    }

    var count = 0
    var left = 0
    var max = Integer.MIN_VALUE
    for (right in 0 until l) {
        if (arr[right] == 0) count++
        while(count > k) {
            if (arr[left] == 0) count--
            left++
        }

        max = Math.max(max, right - left + 1)
    }

    println(max)
}