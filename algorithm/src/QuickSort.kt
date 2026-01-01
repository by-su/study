import sun.nio.cs.Surrogate.high


class QuickSort {

    fun quickSort(arr: IntArray, low: Int = 0, high: Int = arr.size - 1) {
        if (low < high) {
            val pivotIndex = partition(arr, low, high)
            quickSort(arr, low, pivotIndex - 1)
            quickSort(arr, pivotIndex + 1, high)
        }
    }

    fun partition(arr: IntArray, low: Int, high: Int): Int {
        val pivot = arr[low]
        var i = low + 1
        var j = high

        while (i <= j) {
            while (i <= high && arr[i] <= pivot) {
                i++
            }

            while (j >= low && arr[j] > pivot) {
                j--
            }

            if (i < j) {
                arr[i] = arr[j].also { arr[j] = arr[i] }
            }
        }

        arr[low] = arr[j].also { arr[j] = arr[low] }
        return j
    }
}

fun main() {
    val numbers = intArrayOf(10, 30, 20)
    println("정렬 전: ${numbers.contentToString()}")

    val quickSort = QuickSort()
    quickSort.quickSort(numbers)
    println("정렬 후: ${numbers.contentToString()}")
}