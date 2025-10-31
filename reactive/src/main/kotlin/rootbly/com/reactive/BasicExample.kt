package rootbly.com.reactive

import kotlinx.coroutines.reactor.flux
import reactor.core.publisher.Flux

/**
 * 1부터 10까지의 숫자 스트림을 만들어서
 * 짝수만 필터링하고
 * 각 숫자를 제곱하고
 * 결과를 출력하기
 */
fun main() {
    Flux.range(1, 10)
        .filter { it % 2 == 0 }
        .doOnNext { println("Filtered: $it") }
        .map { it * it }
        .doOnNext { println("Product: $it") }
        .subscribe { println(it) }
}