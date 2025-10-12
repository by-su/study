package functional

class LambdaUsingImplicitParameter {
    val add: (Int, Int) -> Int = { i, j -> i + j }
    val printNum: (Int) -> Unit = { println(it) }
    val triple: (Int) -> Int = { it * 3 }
}