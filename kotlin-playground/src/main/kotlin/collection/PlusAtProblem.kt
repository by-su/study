package collection

fun List<Int>.plusAt(index: Int, value: Int): List<Int> {
    val result = mutableListOf<Int>()
    for ((i, v) in this.withIndex()) {
        if (i == index) {
            result.add(value)
        }
        result.add(v)
    }

    if (index == this.size) result.add(value)
    return result
}

fun <T> List<T>.plusAt(index: Int, element: T): List<T> {
    require(index in 0..size)
    val result = toMutableList()
    result.add(index, element)
    return result
}

fun <T> List<T>.plusAt2(index: Int, element: T): List<T> {
    require (index in 0..size)
    return take(index) + element + drop(index)
}

fun <T> List<T>.plusAt3(index: Int, element: T): List<T> {
    require (index in 0..size)
    return when (index) {
        0 -> listOf(element) + this
        size -> this + element
        else -> flatMapIndexed { i, e ->
            if (i == index) listOf(element, e)
            else listOf(e)
        }
    }
}


fun main() {
    val list = listOf(1, 2, 3)
    println(list.plusAt(1, 4))
    println(list.plusAt(0, 5))
    println(list.plusAt(3, 6))
//    val list2 = listOf("A", "B", "C")
//    println(list2.plusAt(1, "D"))
}

