class P68645 {
    fun solution(n: Int): IntArray {
        val snail = Array(n) { i -> IntArray(i + 1) { 0 } }


        var x = 0
        var y = 0
        var angle = 0

        var count = 1
        val dx = intArrayOf(0, 1, -1)
        val dy = intArrayOf(1, 0, -1)
        
        val until = n * (n + 1) / 2
        
        for (i in 1 .. until) {
            
            snail[y][x] = count
            
            val nx = x + dx[angle]
            val ny = y + dy[angle]
            count++

            if (0 <= nx && nx < n && 0 <= ny && ny < n && snail[ny][nx] == 0) {
                x = nx
                y = ny
            } else {
                angle = (angle + 1) % 3
                y += dy[angle]
                x += dx[angle]
            }

        }

        val list = mutableListOf<Int>()
        for (i in 0 until n) {
            for (j in 0..i) {
                list.add(snail[i][j])
            }
        }
        return list.toIntArray()
    }
}

fun main() {
    val p68645 = P68645()
    println(p68645.solution(3))
}