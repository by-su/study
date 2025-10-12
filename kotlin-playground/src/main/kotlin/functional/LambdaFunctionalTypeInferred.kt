package functional

class LambdaFunctionalTypeInferred {
    val add = { i: Int, j: Int -> i + j }
    val printNum = { i : Int -> println(i) }
    val triple = { i: Int -> i * 3 }
}