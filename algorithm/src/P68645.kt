
class P68645 {
    class Solution {
        fun solution(n: Int): IntArray {
            val snail = Array(n) { i -> IntArray(i + 1) { 0 } }

            var x = 0
            var y = 0
            var angle = 0

            var count = 1
            val dx = intArrayOf(0, 1, -1)
            val dy = intArrayOf(1, 0, -1)

            val until = n * (n + 1) / 2

            while(count <= until) {
                snail[y][x] = count

                val ny = y + dy[angle]
                val nx = x + dx[angle]
                count++

                if (0 <= nx && nx < n && 0 <= ny && ny <n && snail[ny][nx] == 0) {
                    y = ny
                    x = nx
                } else {
                    angle = (angle + 1) % 3
                    y = y + dy[angle]
                    x = x + dx[angle]
                }
            }

            return snail.flatMap { it.toList() }.toIntArray()
        }
    }
}