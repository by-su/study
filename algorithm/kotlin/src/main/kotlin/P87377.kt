class P87377 {
    fun solution(line: Array<IntArray>): Array<String> {

        val starArr = mutableListOf<Pair<Int, Int>>()

        var minX = Integer.MAX_VALUE
        var minY = Integer.MAX_VALUE
        var maxX = Integer.MIN_VALUE
        var maxY = Integer.MIN_VALUE
        for (i in 0 until line.size) {
            val (a, b, e) = line[i]
            for (j in i + 1 until line.size) {
                val (c, d, f) = line[j]

                val denominator = a * d - b * c
                if (denominator == 0) continue

                val x = (b * f - e * d) / (a * d - b * c).toDouble()
                val y = (e * c - a * f) / (a * d - b * c).toDouble()

                val intX = x.toInt()
                val intY = y.toInt()
                if (x == intX.toDouble() && y == intY.toDouble()) {
                    starArr.add(Pair(intX, intY))

                    if (minX > intX) minX = intX
                    if (minY > intY) minY = intY
                    if (maxX < intX) maxX = intX
                    if (maxY < intY) maxY = intY
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
    val line = arrayOf(
        intArrayOf(2, -1, 4),
        intArrayOf(-2, -1, 4),
        intArrayOf(0, -1, 1),
        intArrayOf(5, -8, -12),
        intArrayOf(5, 8, 12)
    )

    val p87377 = P87377()
    val result = p87377.solution(line)

    println("=== 결과 ===")
    result.forEach { println(it) }

    println("\n=== 디버깅 정보 ===")
    println("결과 배열 크기: ${result.size} x ${result[0].length}")
}