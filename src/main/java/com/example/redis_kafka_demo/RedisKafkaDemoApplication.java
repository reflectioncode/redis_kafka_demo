package com.example.redis_kafka_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class RedisKafkaDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisKafkaDemoApplication.class, args);
	}
}
