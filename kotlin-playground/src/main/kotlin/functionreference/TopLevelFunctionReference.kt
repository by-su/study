package functionreference

fun add(a: Int, b: Int) = a + b

fun zeroComplex(): Complex = Complex(0.0, 0.0)

fun makeComplex(
    real: Double = 0.0,
    imaginary: Double = 0.0
) = Complex(real, imaginary)

data class Complex(val real: Double, val imaginary: Double)

fun main() {

    val a = ::add
    println(a(2, 3))

    val f1 = ::zeroComplex
    println(f1())

    val f2 = ::makeComplex
    println(f2(0.1, 0.2))
}