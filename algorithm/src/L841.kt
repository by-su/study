class L841 {
    class Solution {
        fun canVisitAllRooms(rooms: List<List<Int>>): Boolean {
            val visited = BooleanArray(rooms.size)

            fun dfs(room: Int) {
                visited[room] = true

                for (key in rooms[room]) {
                    if (!visited[key]) {
                        dfs(key)
                    }
                }
            }

            dfs(0)

            return visited.all { it }
        }
    }
}