package rootbly.com.reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration


fun main() {
    val startTime = System.currentTimeMillis()

    Flux.fromIterable(listOf(1,2,3,4,5))
        // 순서를 보장하고 싶으면 flatMapSequential사용
        .flatMap { id ->
            Mono.delay(Duration.ofMillis(500))
                .map { createUser(id) }
        }
        .subscribe(
            { user -> println(user) },
            { error -> println("Error: $error") },
            {
                val endTime = System.currentTimeMillis()
                println("소요 시간: ${endTime - startTime}ms")
            }
        )

    Thread.sleep(2000)
}

private fun createUser(id: Int): User {
    return User(id, "사용자-$id")
}

private data class User(
    val id: Int,
    val name: String
)