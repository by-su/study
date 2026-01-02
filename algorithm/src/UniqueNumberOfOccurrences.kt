class UniqueNumberOfOccurrences {
    class Solution {
        fun uniqueOccurrences(arr: IntArray): Boolean {
            val map = HashMap<Int, Int>()
            arr.forEach {
                map.put(it, map.getOrDefault(it, 0) + 1)
            }

            val frequencies = map.values
            return frequencies.size == frequencies.toSet().size
        }
    }
}