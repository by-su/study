import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val stair = IntArray(n + 1)
    
    for (i in 1..n) {
        stair[i] = sc.nextInt()
    }
    
    if (n == 1) {
        println(stair[1])
        return
    }
    
    val dp = Array(n + 1) { IntArray(3) }
    
    dp[1][1] = stair[1]
    dp[1][2] = stair[1]
    
    if (n >= 2) {
        dp[2][1] = stair[1] + stair[2]
        dp[2][2] = stair[2]
    }
    
    for (i in 3..n) {
        dp[i][1] = dp[i - 1][2] + stair[i]
        dp[i][2] = maxOf(dp[i-2][1], dp[i-2][2]) + stair[i]
    }
    
    println(maxOf(dp[n][1], dp[n][2]))
}