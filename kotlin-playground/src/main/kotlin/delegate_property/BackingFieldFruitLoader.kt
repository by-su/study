package delegate_property

class LazyStore(val name: String) {
    private var _frutis: List<Fruit>? = null
    val fruits: List<Fruit>
        get() {
            if (_frutis == null) {
                loadFrutis()
            }

            return _frutis!!
        }
}

private fun loadFrutis(): List<Fruit> {
    return listOf<Fruit>(
        Fruit("망고"),
        Fruit("사과"),
        Fruit("딸기"),
        Fruit("멜론"),
        Fruit("포도"),
    )
}