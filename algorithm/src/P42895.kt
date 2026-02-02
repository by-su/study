class P42895 {

    class Solution {
        fun solution(N: Int, number: Int): Int {
            if (N == number) return 1

            val dp = Array(9) { mutableSetOf<Int>() }

            var repeated = 0
            for (i in 1..8) {
                repeated =repeated * 10 + N
                dp[i].add(repeated)

                for (j in 1 until i) {
                    for (a in dp[j]) {
                        for (b in dp[i - j]) {
                            dp[i].add(a+b)
                            dp[i].add(a-b)
                            dp[i].add(a*b)
                            if (b != 0) dp[i].add(a/b)
                        }
                    }
                }

                if (number in dp[i]) return i
            }

            return -1
        }
    }
}