package delegate_property.generic

fun <T> swapElements(arr: Array<T>, i: Int, j: Int) {
    val tmp = arr[i]
    arr[i] = arr[j]
    arr[j] = tmp
}

fun main() {
    val a = arrayOf(1, 2, 3)
    swapElements(a, 0, 2)
    println(a.joinToString())
}