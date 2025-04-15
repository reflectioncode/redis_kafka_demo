package com.example.redis_kafka_demo.kafka.producer;

import com.example.redis_kafka_demo.kafka.event.ProductEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductProducer {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public ProductProducer(KafkaTemplate<String, ProductEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductEvent(ProductEvent event) {
        kafkaTemplate.send("product-events", event);
    }
}
