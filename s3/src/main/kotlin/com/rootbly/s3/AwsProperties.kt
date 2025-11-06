package com.rootbly.s3

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "aws")
class AwsProperties {
    lateinit var s3: S3Properties
    lateinit var credentials: CredentialsProperties
    
    class S3Properties {
        lateinit var bucketName: String
        lateinit var region: String
    }
    
    class CredentialsProperties {
        lateinit var accessKey: String
        lateinit var secretKey: String
    }
}