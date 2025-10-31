package rootbly.com.reactive

import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.netty.http.client.HttpClient
import java.lang.reflect.Member

data class Post(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String = "",
    val body: String = ""
)

fun main() {
    val webClient = WebClient.create("http://jsonplaceholder.typicode.com")

    // 단일 조회 테스트
    val post = webClient.get()
        .uri("/posts/1")
        .retrieve()
        .bodyToMono<Post>()
        .block()

    println("단일 조회: $post")

    // 이제 여러 개 동시 조회 (미션)
    val startTime = System.currentTimeMillis()

    Flux.range(1, 5)
        .flatMap { id ->
            webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono<Post>()
        }
        .collectList()
        .block()
        ?.let { posts ->
            val endTime = System.currentTimeMillis()
            println("\n동시 조회 결과:")
            posts.forEach { println(it.title) }
            println("\n소요 시간: ${endTime - startTime}ms")
        }
}