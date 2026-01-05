class BinarySearch {

    fun sort(arr: IntArray, low: Int = 0, high: Int = arr.size - 1) {
        if (low < high) {
            val pivot = partition(arr, low, high)
            sort(arr, low, pivot - 1)
            sort(arr, pivot + 1, high)
        }
    }

    fun partition(arr: IntArray, low: Int, high: Int): Int {
        val pivot = arr[low]
        var i = low + 1
        var j = high

        while (i <= j) {
            while (i <= high && arr[i] <= pivot) i++
            while (j > low && arr[j] > pivot) j--

            if (i < j) {
                arr[i] = arr[j].also { arr[j] = arr[i] }
            }
        }

        arr[low] = arr[j].also { arr[j] = arr[low] }

        return j
    }

}
