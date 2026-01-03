import javax.swing.tree.TreeNode

class MaximumDeptOfBinaryTree {

    /**
     * Example:
     * var ti = TreeNode(5)
     * var v = ti.`val`
     * Definition for a binary tree node.
     * class TreeNode(var `val`: Int) {
     *     var left: TreeNode? = null
     *     var right: TreeNode? = null
     * }
     */
    class Solution {
        fun maxDepth(root: TreeNode?): Int {
            if (root == null) return 0

            val leftDept = maxDepth(root.left)
            val rightDept = maxDepth(root.right)

            return maxOf(leftDept, rightDept) + 1
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}