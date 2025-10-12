package functional

class AnonumousFunctionalTypeInferred {

    val add = fun(i: Int, j: Int) = i + j

    val printNum = fun(num: Int) {
        println(num)
    }

    val triple = fun(num: Int) = num * 3
}