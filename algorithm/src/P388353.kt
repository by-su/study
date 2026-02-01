import java.util.ArrayDeque

class P388353 {

    class Solution {
        fun solution(storage: Array<String>, requests: Array<String>): Int {
            val n = storage.size
            val m = storage[0].length
            val grid = Array(n) { i -> storage[i].toCharArray() }

            val dx = intArrayOf(1, -1, 0, 0)
            val dy = intArrayOf(0, 0, 1, -1)

            fun inRange(x: Int, y: Int) = x in 0 until n && y in 0 until m

            for (req in requests) {
                val target = req[0]

                // 🚧 크레인
                if (req.length == 2) {
                    for (i in 0 until n) {
                        for (j in 0 until m) {
                            if (grid[i][j] == target) {
                                grid[i][j] = '.'
                            }
                        }
                    }
                    continue
                }

                // 🚜 지게차
                val outsideEmpty = Array(n) { BooleanArray(m) }
                val q: ArrayDeque<Pair<Int, Int>> = ArrayDeque()

                // 테두리의 빈 공간에서 BFS 시작
                for (i in 0 until n) {
                    for (j in 0 until m) {
                        if ((i == 0 || i == n - 1 || j == 0 || j == m - 1) && grid[i][j] == '.') {
                            outsideEmpty[i][j] = true
                            q.add(i to j)
                        }
                    }
                }

                while (q.isNotEmpty()) {
                    val (x, y) = q.poll()
                    for (d in 0..3) {
                        val nx = x + dx[d]
                        val ny = y + dy[d]
                        if (inRange(nx, ny) && !outsideEmpty[nx][ny] && grid[nx][ny] == '.') {
                            outsideEmpty[nx][ny] = true
                            q.add(nx to ny)
                        }
                    }
                }

                val removeList = mutableListOf<Pair<Int, Int>>()

                for (i in 0 until n) {
                    for (j in 0 until m) {
                        if (grid[i][j] != target) continue

                        // 가장자리면 바로 접근 가능
                        if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                            removeList.add(i to j)
                            continue
                        }

                        // 외부와 연결된 빈 공간과 인접한지 확인
                        for (d in 0..3) {
                            val ni = i + dx[d]
                            val nj = j + dy[d]
                            if (inRange(ni, nj) && grid[ni][nj] == '.' && outsideEmpty[ni][nj]) {
                                removeList.add(i to j)
                                break
                            }
                        }
                    }
                }

                // 실제 제거
                for ((x, y) in removeList) {
                    grid[x][y] = '.'
                }
            }

            // 남은 컨테이너 수 세기
            var answer = 0
            for (i in 0 until n) {
                for (j in 0 until m) {
                    if (grid[i][j] != '.') answer++
                }
            }
            return answer
        }
    }
}