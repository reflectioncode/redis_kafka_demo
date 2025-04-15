package com.example.redis_kafka_demo.kafka.consumer;

import com.example.redis_kafka_demo.kafka.event.ProductEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProductConsumer.class);

    @KafkaListener(topics = "product-events", groupId = "product-group")
    public void consume(ProductEvent event) {
        logger.info("Consumed event: {}", event);
    }
}
