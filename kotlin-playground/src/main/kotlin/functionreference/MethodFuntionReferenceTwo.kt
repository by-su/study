package functionreference


class TeamPoints(val points: List<Int>) {
    fun <T> calculatePoints(operation: (List<Int>) -> T): T = operation(points)
}

fun main() {
    val teamPoints = TeamPoints(listOf(1, 3, 5))

    val sum = teamPoints.calculatePoints(List<Int>::sum)
    println(sum)

    val avg = teamPoints.calculatePoints(List<Int>::average)
    println(avg)

    val invalid = String?::isNullOrBlank
    println(invalid(null))
    println(invalid(" "))
    println(invalid("AAA"))

}