class L437 {
    class Solution {
        fun pathSum(root: TreeNode?, targetSum: Int): Int {
            val prefixSumMap = mutableMapOf<Long, Int>()

            prefixSumMap[0L] = 1

            return dfs(root, 0, targetSum.toLong(), prefixSumMap)
        }

        private fun dfs(
            node: TreeNode?,
            currentSum: Long,
            targetSum: Long,
            map: MutableMap<Long, Int>
        ): Int {
            if (node == null) return 0

            val newSum = currentSum + node.`val`

            val targetPathCount = map.getOrDefault(newSum - targetSum, 0)

            map[newSum] = map.getOrDefault(newSum, 0) + 1

            val result = targetPathCount + dfs(node.left, newSum, targetSum, map) + dfs(node.right, newSum, targetSum, map)

            map[newSum] = map[newSum]!! - 1

            return result
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}