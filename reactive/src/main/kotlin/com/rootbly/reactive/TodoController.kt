package com.rootbly.reactive

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class TodoController(
    private val todoRepository: TodoRepository
) {

    @GetMapping("/todos")
    fun getTodos(): Flux<Todo> {
        return todoRepository.findAll()
    }

    @GetMapping("/todos/{id}")
    fun getTodo(@PathVariable id: Int): Mono<Todo> {
        return Mono.just(Todo(id, "Todo $id", false))
    }

    @GetMapping("/todos/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getTodosStream(): Flux<Todo> {
        return todoRepository.findAll()
    }
}