package delegate_property
class Store(val name: String) {
    val fruits: List<Fruit> by lazy { loadFrutis() }
}

data class Fruit(val name: String)

private fun loadFrutis(): List<Fruit> {
    return listOf<Fruit>(
        Fruit("망고"),
        Fruit("사과"),
        Fruit("딸기"),
        Fruit("멜론"),
        Fruit("포도"),
    )
}