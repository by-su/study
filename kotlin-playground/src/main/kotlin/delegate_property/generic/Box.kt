package delegate_property.generic

class Box<T : Any> {
    private lateinit var _content: T
    var content: T
        get() = _content
        set(value) { _content = value }

    override fun toString(): String {
        return "Box(content=$content)"
    }


}
fun main() {
    val box = Box<Int>()
    box.content = 10
    println(box)
}
