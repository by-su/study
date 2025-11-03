import kotlin.math.min

class P77485 {
    fun solution(rows: Int, columns: Int, queries: Array<IntArray>): IntArray {
        var answer = IntArray(queries.size)

        var matrix = generateSecMatrix(rows, columns)


        for ((index, arr) in queries.withIndex()) {
            val r1 = arr[0]
            val c1 = arr[1]
            val r2 = arr[2]
            val c2 = arr[3]

            val (newMatrix, minValue) = rotate(r1 - 1, c1 - 1, r2 - 1, c2 - 1, matrix)
            matrix = newMatrix
            answer[index] = minValue
        }

        return answer
    }

    fun generateSecMatrix(rows: Int, columns: Int): Array<IntArray> {
        var value = 1
        return Array(rows) {
            IntArray(columns) { value++ }
        }
    }

    fun rotate(r1: Int, c1: Int, r2: Int, c2: Int, matrix: Array<IntArray>): Pair<Array<IntArray>, Int> {

        val first = matrix[r1][c1]
        var min_value = first

        for (k in r1 until r2) {
            matrix[k][c1] = matrix[k + 1][c1]
            min_value = min(min_value, matrix[k][c1])
        }

        for (k in c1 until c2) {
            matrix[r2][k] = matrix[r2][k + 1]
            min_value = min(min_value, matrix[r2][k])
        }

        for (k in r2 downTo r1 + 1) {
            matrix[k][c2] = matrix[k - 1][c2]
            min_value = min(min_value, matrix[k][c2])
        }

        for (k in c2 downTo c1 + 2) {
            matrix[r1][k] = matrix[r1][k - 1]
            min_value = min(min_value, matrix[r1][k])
        }

        matrix[r1][c1 + 1] = first

        return Pair(matrix, min_value)
    }
}