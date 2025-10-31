package com.rootbly.reactive

import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Repository
class TodoRepository {

    private val todos = listOf(
        Todo(1, "Spring WebFlux 공부", false),
        Todo(2, "리액티브 프로그래밍 실습", false),
        Todo(3, "BackPressure 이해하기", false)
    )

    fun findAll(): Flux<Todo> {
        return Flux.fromIterable(todos)
            .delayElements(Duration.ofMillis(300))
    }

    fun findById(id: Int): Mono<Todo> {
        return Mono.justOrEmpty(todos.find { it.id == id })
            .delayElement(Duration.ofMillis(300))
    }
}