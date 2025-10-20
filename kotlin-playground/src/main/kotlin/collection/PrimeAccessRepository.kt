package collection

import kotlin.random.Random
import kotlin.system.measureTimeMillis

class PrimeAccessList(
    val entries: List<PrimeAccessEntry>
)

class PrimeAccessEntry(
    val userId: String,
    val allowList: Boolean,
    val denyList: Boolean
)

class PrimeAccessRepository(
    private val primeAccessList: PrimeAccessList
) {

    private val entries = primeAccessList.entries
        .associateBy { it.userId }

    fun isOnAllowList(userId: String): Boolean = entries[userId]?.allowList ?: false

    fun isOnDenyList(userId: String) : Boolean = entries[userId]?.denyList ?: false
}

fun main() {
    val entries = List(200_000) {
        PrimeAccessEntry(
            userId = it.toString(),
            allowList = Random.nextBoolean(),
            denyList = Random.nextBoolean()
        )
    }.shuffled()

    val accessList = PrimeAccessList(entries)
    val repo = PrimeAccessRepository(accessList)
    measureTimeMillis { }.let { println("Class creation took $it ms")}

    measureTimeMillis {
        for (userId in 1L..10_000L) {
            repo.isOnAllowList(userId.toString())
        }
    }.let { println("Operation took $it ms") }

    measureTimeMillis {
        for (userId in 1L..10_000L) {
            repo.isOnDenyList(userId.toString())
        }
    }.let { println("Operation took $it ms") }
}