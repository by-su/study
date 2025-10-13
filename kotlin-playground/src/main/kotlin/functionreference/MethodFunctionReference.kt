package functionreference

data class Number(val num: Int) {
    fun toFloat(): Float = num.toFloat()
    fun times(n: Int): Number = Number(num * n)
}


// 멤버 함수를 참조하려면 리시버의 타입부터 명시한 다음 ::와 메서드 이름을 적어야 함.
fun main() {

    val numberObject = Number(10)

    val float: (Number) -> Float = Number::toFloat
    // toFloat에는 매개변수가 없지만 그 함수 타입에는 Number타입의 리시버가 필요
    println(float(numberObject))

    val multiply: (Number, Int) -> Number = Number::times
    println(multiply(numberObject, 2))

}