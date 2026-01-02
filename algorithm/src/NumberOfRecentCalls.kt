class NumberOfRecentCalls {

    class RecentCounter() {

        val queue = ArrayDeque<Int>()

        fun ping(t: Int): Int {

            queue.add(t)

            while (queue.isNotEmpty() && queue.first() < t - 3000) {
                queue.removeFirst()
            }

            return queue.size

        }

    }

}

