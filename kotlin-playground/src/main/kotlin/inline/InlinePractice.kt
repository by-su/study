package inline

import inline.anyOf

inline fun <reified T> Iterable<*>.anyOf(): Boolean {
    for (element in this) {
        if (element is T) return true
    }
    return false
}

inline fun <reified T>Iterable<*>.firstOrNull(): T? {
    for (element in this) {
        if (element is T) return element
    }

    return null
}

inline fun <reified T, reified R> Map<*,*>.filterValuesInstanceOf(): HashMap<T, R> {
    val resultMap = HashMap<T, R>()
    for ((key, value) in this) {
        if (key is T && value is R) {
            resultMap[key] = value
        }
    }

    return resultMap
}

inline fun <reified T> Iterable<*>.anyOf2() {
    any { it is T }
}

inline fun <reified T> Iterable<*>.firstOrNull2(): T? = firstOrNull { it is T } as? T

inline fun <reified T, reified R> Map<*, *>.filterValuesInstanceOf2() = filter { it.key is T && it.value is T } as Map<T, R>

fun main() {
    val list = listOf(1, "A", 3, "B")
    println(list.anyOf<Int>())
    println(list.anyOf<String>())
    println(list.anyOf<Double>())

}