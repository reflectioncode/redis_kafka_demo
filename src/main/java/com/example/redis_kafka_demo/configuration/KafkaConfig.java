package com.example.redis_kafka_demo.configuration;

import com.example.redis_kafka_demo.events.product.ProductEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    //создание топиков
    @Value("${kafka.topics.product.created}")
    private String added_products_topic;
    @Value("${kafka.topics.product.removed}")
    private String removed_products_topic;

    private NewTopic createTopic(String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(3) // количество партиций
                .replicas(3) // количество реплик
                .configs(Map.of("min.insync.replicas", "2")) // минимальное количество реплик, синхронизированных с лидером
                .build();
    }

    @Bean
    public NewTopic productCreatedTopic() {
        return createTopic(added_products_topic);
    }

    @Bean
    public NewTopic productRemovedTopic() {
        return createTopic(removed_products_topic);
    }

    //создание kafkaTemplate
    @Bean
    public ProducerFactory<String, ProductEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        //настройка сериализации ключа и сообщения
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, ProductEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}