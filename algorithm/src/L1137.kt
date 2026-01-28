class L1137 {

    class Solution {
        fun tribonacchi(n: Int): Int {
            if (n == 0) return 0
            if (n <= 2) return 1

            var t0 = 0
            var t1 = 1
            var t2 = 1

            for (i in 3..n) {
                val next = t0 + t1 + t2
                t0 = t1
                t1 = t2
                t2 = next
            }

            return t2
        }
    }
}