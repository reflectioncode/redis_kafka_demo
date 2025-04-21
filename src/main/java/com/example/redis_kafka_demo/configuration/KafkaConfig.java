package com.example.redis_kafka_demo.configuration;

import com.example.redis_kafka_demo.events.product.ProductEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${kafka.topics.product}")
    private String products_topic;

    private NewTopic createTopic(String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(3) // количество партиций
                .replicas(3) // количество реплик
                .configs(Map.of("min.insync.replicas", "2")) // минимальное количество синхронизированных реплик
                .build();
    }

    @Bean
    public NewTopic productCreatedTopic() {
        return createTopic(products_topic);
    }


    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}