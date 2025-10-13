package functionreference

data class NumberTwo(val num: Int) {
    fun toFloat(): Float = num.toFloat()
    fun times(n: Int): Number = Number(num * n)
}

// 타입의 메서드를 참조하는 대신, 특정 객체의 메서드를 참조하는 방법 -> 한정된 함수 참조

fun main() {
    val num = NumberTwo(10)

    val f1: () -> Float = num::toFloat
    println(f1())

    val multiply: (Int) -> Number = num::times
    println(multiply(2))
}