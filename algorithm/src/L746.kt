class L746 {
    class Solution {
        fun minCostClimbingStairs(cost: IntArray): Int {
            val n = cost.size
            val dp = Array<Int>(n) { 0 }

            dp[0] = cost[0]
            dp[1] = cost[1]
            for (i in 2 until n) {
                dp[i] = Math.min(dp[i - 2] + cost[i], dp[i - 1] + cost[i])
            }

            val result = if (dp[n - 2] > dp[n - 1]) dp[n - 1] else dp[n - 2]

            return result

        }
    }
}