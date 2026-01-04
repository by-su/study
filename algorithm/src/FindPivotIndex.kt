class FindPivotIndex {

    class Solution {
        fun pivotIndex(nums: IntArray): Int {

            val n = nums.size
            var sum = nums.sum()
            var left = 0
            for (i in 0 until n) {
                sum -= i
                println(sum)
                if (i != 0) left += nums[left - 1]
                println(left)
                if (left == sum) return i
            }

            return -1


        }
    }
}