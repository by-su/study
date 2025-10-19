package com.rootbly.spring

import com.rootbly.spring.order.DummyService
import com.rootbly.spring.payload.DummyCreateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dummy")
class TestController(
    private val dummyService: DummyService
) {

    @PostMapping
    fun createDummy(@RequestBody request: DummyCreateRequest) = dummyService.generateDummyData(request)

    @GetMapping
    fun getDummy(id: Long) = dummyService.getDummy(id)
}