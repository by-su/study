class QuickSortTwo {

    fun quickSort(arr: IntArray, low: Int, high: Int) {
        if (low < high) {
            val pivot = partition(arr, low, high)
            quickSort(arr, low, pivot - 1)
            quickSort(arr, pivot + 1, high)
        }
    }

    private fun partition(arr: IntArray, low: Int, high: Int): Int {
        val pivot = arr[low]
        var i = low + 1
        var j = high

        while (i <= j) {
            while (low <= j && pivot > arr[j]) j--

            while (i <= high && pivot <= arr[i]) i++

            if (i < j) {
                arr[i] = arr[j].also { arr[j] = arr[i] }
            }
        }

        arr[low] = arr[j].also { arr[j] = arr[low] }
        return j
    }
}