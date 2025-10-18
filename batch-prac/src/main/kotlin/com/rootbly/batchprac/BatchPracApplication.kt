package com.rootbly.batchprac

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class BatchPracApplication

fun main(args: Array<String>) {
    runApplication<BatchPracApplication>(*args)
}
