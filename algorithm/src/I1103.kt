import java.util.Scanner

fun solution(arr: IntArray): Int {
    var answer = 0
    val dy = IntArray(arr.size)
    dy[0] = 1

    for (i in 1 until arr.size) {
        var max = Int.MIN_VALUE
        for (j in i - 1 downTo 0) {
            if (arr[j] < arr[i]) max = maxOf(max, dy[j])
        }
        dy[i] = max + 1
        answer = maxOf(answer, dy[i])
    }

    return answer
}

fun main() {
    val kb = java.util.Scanner(java.lang.System.`in`)
    val n = kb.nextInt()
    val arr = IntArray(n) { kb.nextInt() }
    print(solution(arr))
}