class Dota2Senate {
    class Solution {
        fun predictPartyVictory(senate: String): String {
            val rq = ArrayDeque<Int>()
            val dq = ArrayDeque<Int>()

            val n = senate.length

            senate.forEachIndexed { index, ch ->
                if (ch == 'R') rq.add(index)
                if (ch == 'D') dq.add(index)
            }

            while (rq.isNotEmpty() && dq.isNotEmpty()) {
                val rIndex = rq.removeFirst()
                val dIndex = dq.removeFirst()

                if (rIndex > dIndex) {
                    dq.add(rIndex + n)
                } else {
                    rq.add(dIndex + n)
                }
            }

            return if (rq.isNotEmpty()) return "Radiant" else "Dire"

        }
    }
}