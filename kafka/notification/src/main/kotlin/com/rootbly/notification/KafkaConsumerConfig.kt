package com.rootbly.notification

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer
import org.springframework.util.backoff.FixedBackOff

@Configuration
@EnableKafka
class KafkaConsumerConfig {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val props: MutableMap<String, Any> = HashMap()

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service")
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer::class.java)
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer::class.java)

        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer::class.java)
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer::class.java)

        props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "*")

        props.put(
            JacksonJsonDeserializer.TYPE_MAPPINGS,
            "orderCreated:com.rootbly.notification.OrderCreatedEvent,stockInsufficient:com.rootbly.notification.StockInsufficientEvent"
        )

        return DefaultKafkaConsumerFactory<String, Any>(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory =
            ConcurrentKafkaListenerContainerFactory<String, Any>()

        factory.setConsumerFactory(consumerFactory())

        // 병렬 처리 설정 (쓰레드 수)
        // 파티션 수에 맞춰 설정하는 것이 일반적
        factory.setConcurrency(3)

        // 에러 핸들링 (Spring Kafka 최신 버전 표준)
        // 예: 에러 발생 시 1초 간격으로 2번 재시도 후 넘어가기
        factory.setCommonErrorHandler(
            DefaultErrorHandler(
                FixedBackOff(1000L, 2L)
            )
        )

        return factory
    }
}