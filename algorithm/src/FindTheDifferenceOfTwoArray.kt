class FindTheDifferenceOfTwoArray {
    class Solution {
        fun findDifference(nums1: IntArray, nums2: IntArray): List<List<Int>> {
            val set = nums1.toSet()
            val set2 = nums2.toSet()

            return listOf(
                set.filter { it !in set2 },
                set2.filter { it !in set }
            )

        }
    }
}