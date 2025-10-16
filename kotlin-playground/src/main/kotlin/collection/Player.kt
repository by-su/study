package collection

data class Player(
    val name: String,
    val team: String
) {
}

fun main() {
    val players = listOf(
        Player("Alex", "A"),
        Player("Ben", "B"),
        Player("Cal", "C")
    )
    val grouped = players.groupBy { it.team }
    println(grouped)

    println(grouped.flatMap { it.value })
}