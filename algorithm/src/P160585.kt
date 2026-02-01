import sun.management.MemoryNotifInfoCompositeData.getCount

class P160585 {

    class Solution {
        fun solution(board: Array<String>): Int {

            // char배열로 일단 변환
            val arr = getCharArr(board)

            // O, X 카운트
            val (oCnt, xCnt) = getCount(arr)

            val oWins = checkWinner(arr, 'O')
            val xWins = checkWinner(arr, 'X')

            if (oWins && xWins) return 0
            if (oWins && oCnt <= xCnt) return 0
            if (xWins && xCnt != oCnt) return 0

            if (xWins) return 1
            if (oWins) return 1

            return 1
        }

        private fun checkWinner(arr: Array<CharArray>, player: Char): Boolean {
            // 가로 체크
            for (i in 0..2) {
                if (arr[i].all { it == player }) return true
            }

            // 세로 체크
            for (j in 0..2) {
                if (arr[0][j] == player && arr[1][j] == player && arr[2][j] == player) {
                    return true
                }
            }

            // 대각선 체크
            if (arr[0][0] == player && arr[1][1] == player && arr[2][2] == player) return true
            if (arr[0][2] == player && arr[1][1] == player && arr[2][0] == player) return true

            return false
        }

        private fun getCharArr(board: Array<String>): Array<CharArray> {
            val arr = Array(3) { CharArray(3) }
            for (i in 0 until 3) {
                val charArr = board[i].split("")

                for (j in 0 until 3) {
                    arr[i][j] = board[i][j]
                }
            }

            return arr
        }

        private fun getCount(arr: Array<CharArray>): Pair<Int, Int> {
            var oCount = 0
            var xCount = 0
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    if (arr[i][j] == 'X') {
                        xCount++
                    }
                    if (arr[i][j] == 'O') {
                        oCount++
                    }
                }
            }

            return Pair(oCount, xCount)
        }
    }
}

fun main() {
    val p = P160585.Solution()
    val result = p.solution(arrayOf("O.X", ".O.", "..X"))
}