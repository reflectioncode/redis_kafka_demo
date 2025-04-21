package com.example.demo.configuration;

import com.example.demo.events.product.ProductEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, ProductEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092,localhost:29093,localhost:29094");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "product-event-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Используем JsonDeserializer для десериализации ProductEvent
        JsonDeserializer<ProductEvent> jsonDeserializer = new JsonDeserializer<>(ProductEvent.class);
        ErrorHandlingDeserializer<ProductEvent> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, errorHandlingDeserializer);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProductEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}