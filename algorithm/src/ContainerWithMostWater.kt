import kotlin.math.max
import kotlin.math.min

class ContainerWithMostWater {
    class Solution {
        fun maxArea(height: IntArray): Int {
            var l = 0
            var r = height.size - 1

            var answer = Integer.MIN_VALUE

            while (l < r) {
                val h = min(height[l], height[r])
                val w = r - l
                val area = h * w
                answer = max(answer, area)

                if (height[l] > height[r]) r--
                else l++
            }

            return answer
        }
    }
}