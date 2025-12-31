import kotlin.math.max

class MaximumAverageSubArray {

    class Solution {
        fun findMaxAverage(nums: IntArray, k: Int): Double {
            var sum = 0
            for (i in 0 until k) {
                sum += nums[i]
            }

            var max = sum
            var left = 0
            for (right in k until nums.size) {
                sum -= nums[left]
                sum += nums[right]
                max = max(max, sum)
                left++
            }

            return max / k.toDouble()
        }
    }
}

fun main() {
    val max = MaximumAverageSubArray.Solution()
    val result = max.findMaxAverage(intArrayOf(1, 12, -5, -6, 50, 3), 4)
    println(result)
}