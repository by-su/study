class DetermineIfTwoStringsAreClose {
    class Solution {
        fun closeStrings(word1: String, word2: String): Boolean {
            if (word1.length != word2.length) return false

            val map = HashMap<Char, Int>()
            word1.forEach {
                map.put(it, map.getOrDefault(it, 0) + 1)
            }

            val map2 = HashMap<Char, Int>()
            word2.forEach {
                map2.put(it, map2.getOrDefault(it, 0) + 1)
            }

            return map.keys == map2.keys && map.values.sorted() == map2.values.sorted()
        }
    }
}

fun main() {
    val solution = DetermineIfTwoStringsAreClose.Solution()
    solution.closeStrings("abc", "bca")

}