package functional

fun main() {
    val cheer: () -> Unit = fun() {
        println("Hello")
    }

    cheer.invoke()
    cheer()

    val printNumber: (Int) -> Unit = fun(i: Int) {
        println(i)
    }

    printNumber.invoke(10)
    printNumber(20)


    val log: (String, String) -> Unit = {
        ctx: String, message: String ->
        println("[$ctx] $message")
    }

    data class User(val id: Int)

    val add: (String, String) -> String = { s1: String, s2: String -> s1 + s2 }
}

fun main2() {
    val cheer: () -> Unit = {
        println("Hello")
    }
    cheer.invoke()
    cheer()

    val printNumber: (Int) -> Unit = { i: Int ->
        println(i)
    }
    printNumber.invoke(10)
    printNumber(20)

    val log: (String, String) -> Unit = { ctx, message ->
        println("[$ctx] $message")
    }

    val add: (String, String) -> String = { s1: String, s2: String ->
        s1 + s2
    }
}