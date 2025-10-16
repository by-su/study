package collection


fun main() {
    val names = listOf("Alex", "Ben", "Cal")
    val aW = names.associateWith { it.length }
    println(aW)
    println(aW.keys.toList() == names)
    val aB = names.associateBy { it.first() }
    println(aB)
    println(aB.values.toList() == names)
}