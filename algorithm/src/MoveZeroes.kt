class MoveZeroes {

    class Solution {
        fun moveZeroes(nums: IntArray): Unit {
            var slow = 0

            for (fast in nums.indices) {
                if (nums[fast] != 0) {
                    val temp = nums[slow]
                    nums[slow] = nums[fast]
                    nums[fast] = temp
                    slow++
                }
            }
        }
    }
}

fun main() {
    val s = MoveZeroes.Solution()
    s.moveZeroes(intArrayOf(0,1,0,3,12))
}