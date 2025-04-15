package com.example.redis_kafka_demo.kafka;

import com.example.redis_kafka_demo.kafka.event.ProductEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ProductEventListener.class);

    @KafkaListener(topics = "product-events", groupId = "product-group")
    public void handleProductEvent(@Payload ProductEvent event) {
        logger.info("Received product event: type={}, data={}", event.getEventType(), event.getProduct());
    }
}
