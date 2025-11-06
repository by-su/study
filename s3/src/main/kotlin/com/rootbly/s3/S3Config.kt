package com.rootbly.s3

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3Config(
    private val awsProperties: AwsProperties
) {
    @Bean
    fun s3Client(): S3Client {
        val credentials = AwsBasicCredentials.create(
            awsProperties.credentials.accessKey,
            awsProperties.credentials.secretKey
        )
        
        return S3Client.builder()
            .region(Region.of(awsProperties.s3.region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }
}