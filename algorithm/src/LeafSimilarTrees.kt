import jdk.internal.vm.ThreadContainers.root
import java.lang.reflect.Array.set

class LeafSimilarTrees {
    class Solution {
        fun leafSimilar(root1: TreeNode?, root2: TreeNode?): Boolean {
            val set1 = mutableListOf<Int>()
            dfs(root1!!, set1)

            val set2 = mutableListOf<Int>()
            dfs(root2!!, set2)

            return set1 == set2
        }

        fun dfs(root: TreeNode, list: MutableList<Int>) {
            if (root == null) return

            if (root.left == null && root.right == null) {
                list.add(root.`val`)
                return
            }

            root.left?.let { dfs(it, list) }
            root.right?.let { dfs(it, list) }
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}