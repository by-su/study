package functional

class LambdaFunctionalTypeSpecified {
    val add: (Int, Int) -> Int = { i, j -> i + j }
    val printNum: (Int) -> Unit = { i -> println(i) }
    val triple: (Int) -> Int = { i -> i * 3 }
}