class P389478 {

    fun solution(n: Int, w: Int, num: Int): Int {
        val layer = (num - 1) / w
        val posInLayer = (num - 1) % w

        val column = if (layer % 2 == 0) {
            posInLayer
        } else {
            w - 1 - posInLayer
        }

        var count = 0
        val maxLayer = (n - 1) / w
        for (k in layer..maxLayer) {
            val numberAtColumn = if (k % 2 == 0) {
                k * w + (column + 1)
            } else {
                k * w + (w - column)
            }
            if (numberAtColumn <= n) count++
        }

        return count
    }
}

fun main() {
    val p = P389478()
    println(p.solution(22, 6, 8))
}