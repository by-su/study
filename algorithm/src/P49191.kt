class P49191 {

    class Solution {
        fun solution(n: Int, results: Array<IntArray>): Int {
            // 승패 관계를 저장할 2차원 배열
            // graph[i][j] = true: i가 j를 이김
            val graph = Array(n + 1) { BooleanArray(n + 1) }

            // 직접적인 경기 결과 입력
            for (result in results) {
                val winner = result[0]
                val loser = result[1]
                graph[winner][loser] = true
            }

            // 플로이드-워셜로 간접적인 승패 관계 파악
            // k를 거쳐서 i가 j를 이길 수 있는지 확인
            for (k in 1..n) {
                for (i in 1..n) {
                    for (j in 1..n) {
                        // i가 k를 이기고, k가 j를 이기면 → i는 j를 이김
                        if (graph[i][k] && graph[k][j]) {
                            graph[i][j] = true
                        }
                    }
                }
            }

            var answer = 0

            // 각 선수에 대해 순위를 정확히 매길 수 있는지 확인
            for (i in 1..n) {
                var count = 0

                // i선수와 다른 모든 선수의 승패 관계 확인
                for (j in 1..n) {
                    if (i == j) continue

                    // i가 j를 이기거나, j가 i를 이기면 승패 관계가 명확함
                    if (graph[i][j] || graph[j][i]) {
                        count++
                    }
                }

                // 다른 모든 선수(n-1명)와의 승패가 명확하면 순위 확정 가능
                if (count == n - 1) {
                    answer++
                }
            }

            return answer
        }

    }
}