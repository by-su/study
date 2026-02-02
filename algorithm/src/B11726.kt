import java.util.Scanner

fun main() {
    val sc = java.util.Scanner(java.lang.System.`in`)
    val n = sc.nextInt()

    val dp = Array(n + 1) { 0 }

    if (n == 1) {
        println(1)
        return
    }

    dp[1] = 1
    dp[2] = 2

    for (i in 3..n) {
        dp[i] = dp[i - 1] + dp[i - 2]
    }

    println(dp[n])
}