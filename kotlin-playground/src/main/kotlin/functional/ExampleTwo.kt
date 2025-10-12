package functional


fun call(before: () -> Unit = {}, after: () -> Unit = {}) {
    before()
    print("A")
    after()
}

fun main() {
    call({print("C")})
    call() { print("B") }
}