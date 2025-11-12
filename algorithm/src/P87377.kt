import kotlin.math.max
import kotlin.math.min

class P87377 {
    fun solution(line: Array<IntArray>): Array<String> {
        val starArr = mutableListOf<Pair<Int, Int>>()

        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE

        for (i in 0 until line.size) {
            val (a, b, e) = line[i]
            for (j in i + 1 until line.size) {
                val (c, d, f) = line[j]

                // Long으로 변환해서 연산
                val adbc = a.toLong() * d - b.toLong() * c
                if (adbc == 0L) continue // 평행한 경우

                val bfed = b.toLong() * f - e.toLong() * d
                val ecaf = e.toLong() * c - a.toLong() * f

                val x = bfed.toDouble() / adbc.toDouble()
                val y = ecaf.toDouble() / adbc.toDouble()

                val intX = x.toInt()
                val intY = y.toInt()
                if (x == intX.toDouble() && y == intY.toDouble()) {
                    starArr.add(Pair(intX, intY))

                    minX = min(minX, intX)
                    minY = min(minY, intY)
                    maxX = max(maxX, intX)
                    maxY = max(maxY, intY)
                }
            }
        }

        val xLen = maxX - minX + 1
        val yLen = maxY - minY + 1
        val coord = Array(yLen) { CharArray(xLen) { '.' } }

        for ((starX, starY) in starArr) {
            val nx = starX - minX
            val ny = starY - minY
            coord[ny][nx] = '*'
        }

        val answer = mutableListOf<String>()
        for (result in coord) {
            answer.add(result.joinToString(""))
        }

        return answer.reversed().toTypedArray()
    }
}

fun main() {
    val pr = P87377()
    pr.solution(
        arrayOf(
            intArrayOf(2, -1, 4),
            intArrayOf(-2, -1, 4),
            intArrayOf(0, -1, 1),
            intArrayOf(5, -8, -12),
            intArrayOf(5, 8, 12)
        )
    )
}
