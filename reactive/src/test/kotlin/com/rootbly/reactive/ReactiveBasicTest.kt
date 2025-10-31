package com.rootbly.reactive

import reactor.core.publisher.Flux
import java.time.Duration
import kotlin.test.Test

class ReactiveBasicTest {

    /**
     * flux는 동기적으로 데이터를 발행
     * subscribe가 끝나야 다음 코드가 실행됨.
     */
    @Test
    fun justSyncFlux() {
        val flux = Flux.just(1, 2, 3, 4, 5)

        println("Flux 생성 완료")  // 1번

        flux.subscribe(
            { println("받은 데이터: $it") },
            { println("에러 발생: $it")},
            { println("완료") }
        )

        println("subscribe 호출 완료")
    }

    /**
     * 비동기 테스트
     */
    @Test
    fun asyncFlux() {
        val flux = Flux.range(1, 5)
            .delayElements(Duration.ofMillis(100))

        println("Flux 생성 완료")

        flux.subscribe(
            { data -> println("받은 데이터: $data") },
            { error -> println("에러 발생: $error") },
            { println("완료!") }
        )

        println("subscribe 호출 완료")

        Thread.sleep(1000)
    }

    @Test
    fun lazyEvaluationTest() {
        println("1. 시작")

        val flux = Flux.range(1, 5)
            .doOnNext { println("    발행: $it") }
            .map { it * 2 }
            .doOnNext { println("    변환: $it") }

        println("2. Flux 생성 완료")

        Thread.sleep(2000)

        println("3. subscribe 호출")
        flux.subscribe { println("     최종 데이터: $it")}

        println("4. 끝")
    }

    @Test
    fun mapVsFlatMapTest() {
        println("=== map 사용 ===")
        Flux.range(1, 3)
            .map {
                println("  map: $it 처리 중...")
                it * 2
            }
            .subscribe { println("    결과: $it") }

        println("\n=== flatMap 사용 ===")
        Flux.range(1, 3)
            .flatMap {
                println("  flatMap: $it 처리 중...")
                Flux.just(it * 2, it * 3)
            }
            .subscribe { println("    결과: $it") }
    }

    @Test
    fun realDifferenceTest() {
        println("=== map 사용 ===")
        Flux.just(1, 2, 3)
            .map { userId -> getOrders(userId) }
            .subscribe {
                println("받은 것: $it")
                // 여기서 it은 Flux 객체! 값이 아니에요
            }

        println("\n=== flatMap 사용 ===")
        Flux.just(1, 2, 3)
            .flatMap { userId -> getOrders(userId) }
            .subscribe {
                println("받은 것: $it")
                // 여기서 it은 String 값!
            }
    }

    private fun getOrders(userId: Int): Flux<String> {
        return Flux.just("주문${userId}-A", "주문${userId}-B")
    }

}