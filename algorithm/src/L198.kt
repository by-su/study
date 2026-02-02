class Solution {
    fun rob(nums: IntArray): Int {
        val n = nums.size
        val arr = IntArray(n) { 0 }
        arr[0] = nums[0]

        if (n == 1) return nums[0]

        arr[1] = maxOf(nums[0], nums[1])

        for (i in 2 until n) {
            arr[i] = maxOf(arr[i - 1], arr[i - 2] + nums[i])
        }

        return arr[n - 1]
    }
}