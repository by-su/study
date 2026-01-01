import java.util.Stack

class RemovingStarsFromAString {
    class Solution {
        fun removeStars(s: String): String {
            val stack = Stack<Char>()

            for (ch in s) {
                if (ch == '*') {
                    if (stack.isNotEmpty()) stack.pop()
                } else {
                    stack.push(ch)
                }
            }

            val sb = StringBuilder()
            stack.forEach { sb.append(it) }

            return sb.toString()
        }
    }
}