import java.util.*

class Palindrome {
    fun solution(s: String): Array<String> {
        val sH = HashMap<Char, Int>()
        val len = s.length

        // 문자 빈도수 계산
        s.forEach { c ->
            sH[c] = sH.getOrDefault(c, 0) + 1
        }

        // 홀수 개수 문자 확인
        var odd = 0
        var mid = '#'
        for ((key, count) in sH) {
            if (count % 2 == 1) {
                mid = key
                odd++
            }
        }

        // 홀수 개수 문자가 2개 이상이면 회문 불가능
        if (odd > 1) return emptyArray()

        val tmp = LinkedList<Char>()
        val res = mutableListOf<String>()

        // 홀수 개수 문자가 있으면 중앙에 배치
        if (mid != '#') {
            tmp.add(mid)
            sH[mid] = sH[mid]!! - 1
        }

        dfs(tmp, sH, res, len)

        return res.toTypedArray()
    }

    private fun dfs(
        tmp: LinkedList<Char>,
        sH: HashMap<Char, Int>,
        res: MutableList<String>,
        len: Int
    ) {
        if (tmp.size == len) {
            val sb = StringBuilder()
            tmp.forEach { c -> sb.append(c) }
            res.add(sb.toString())
        } else {
            for ((key, count) in sH) {
                if (count == 0) continue

                tmp.addFirst(key)
                tmp.addLast(key)
                sH[key] = count - 2
                dfs(tmp, sH, res, len)
                tmp.removeFirst()
                tmp.removeLast()
                sH[key] = count
            }
        }
    }
}