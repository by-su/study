import java.util.LinkedList
import java.util.Queue

class P49189 {

    class Solution {
        fun solution(n: Int, edge: Array<IntArray>): Int {
            val list = MutableList(n + 1) { mutableListOf<Int>() }

            for (arr in edge) {
                val first = arr[0]
                val second = arr[1]

                list[first].add(second)
                list[second].add(first)
            }

            val array = Array(n + 1) { 0 }
            val queue: Queue<Int> = LinkedList<Int>()
            queue.add(1)
            array[1] = 1

            var max = 0
            while (queue.isNotEmpty()) {
                max++
                val len = queue.size

                for (i in 0 until len) {
                    val current = queue.poll()

                    for (next in list[current]) {
                        if (array[next] == 0) {
                            array[next] = array[current] + 1
                            queue.add(next)
                        }
                    }
                }
            }

            return array.filter { it == max }.size
        }
    }
}