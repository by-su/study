package com.rootbly.consumer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer
import org.springframework.kafka.support.serializer.JacksonJsonSerializer
import org.springframework.util.backoff.ExponentialBackOff
import org.springframework.util.backoff.FixedBackOff
import java.util.function.BiFunction


@Configuration
@EnableKafka
class KafkaConsumerConfig {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val props: MutableMap<String, Any> = HashMap()

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-service")
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer::class.java)
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer::class.java)

        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer::class.java)
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer::class.java)

        props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "com.rootbly.producer")

        props.put(JacksonJsonDeserializer.TYPE_MAPPINGS,
            "orderCreated:com.rootbly.consumer.OrderCreatedEvent")

        return DefaultKafkaConsumerFactory<String, Any>(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(
        dlqKafkaTemplate: KafkaTemplate<String, Any>
    ): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory =
            ConcurrentKafkaListenerContainerFactory<String, Any>()

        factory.setConsumerFactory(consumerFactory())

        val recoverer = DeadLetterPublishingRecoverer(
            dlqKafkaTemplate,
            BiFunction { record, _ ->
                TopicPartition(record.topic() + ".DLT", record.partition())
            }
        )

        val errorHandler = DefaultErrorHandler(
            recoverer,
            ExponentialBackOff().apply {
                initialInterval = 1000L
                multiplier = 2.0
                maxInterval = 10000L
                maxElapsedTime = 15000L
            }
        )

        errorHandler.addNotRetryableExceptions(
            IllegalArgumentException::class.java
        )

        factory.setCommonErrorHandler(errorHandler)

        // 병렬 처리 설정 (쓰레드 수)
        // 파티션 수에 맞춰 설정하는 것이 일반적
        factory.setConcurrency(3)

        return factory
    }
}