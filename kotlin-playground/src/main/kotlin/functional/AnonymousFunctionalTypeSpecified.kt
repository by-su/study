package functional

class AnonymousFunctionalTypeSpecified {
    val add: (Int, Int) -> Int =  fun(i: Int, j: Int): Int {
        return i + j
    }

    val printNum: (Int) -> Unit = fun(num: Int) {
        println(num)
    }

    val triple: (Int) -> Int = fun(num: Int): Int {
        return num * 3
    }
}