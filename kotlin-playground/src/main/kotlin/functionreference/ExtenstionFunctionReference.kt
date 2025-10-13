package functionreference


fun Number.toFloat(): Float = num.toFloat()
fun Number.times(n: Int): Number = Number(num * n)

fun main() {
    val num = Number(10)

    val float: (Number) -> Float = Number::toFloat
    println(float(num))

    val multiply: (Number, Int) -> Number = Number::times
    println(multiply(num, 3))
}