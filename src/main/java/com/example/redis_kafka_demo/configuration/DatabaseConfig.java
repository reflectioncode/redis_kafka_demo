package com.example.redis_kafka_demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class DatabaseConfig {
    // Можем указать аудирование createdAt и updatedAt через @CreatedDate, @LastModifiedDate
}
