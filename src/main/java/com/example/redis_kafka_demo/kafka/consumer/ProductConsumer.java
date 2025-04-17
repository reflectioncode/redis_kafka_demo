package com.example.redis_kafka_demo.kafka.consumer;

import com.example.redis_kafka_demo.kafka.event.ProductEvent;
import com.example.redis_kafka_demo.model.entity.ProductEventLog;
import com.example.redis_kafka_demo.repository.ProductEventLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProductConsumer.class);

    private final ProductEventLogRepository eventLogRepository;

    public ProductConsumer(ProductEventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @KafkaListener(topics = "product-events", groupId = "product-group")
    public void consume(ProductEvent event) {
        if (event != null && event.getProduct() != null) {
            logger.info("Received event: Type = {}, Product = {}", event.getEventType(), event.getProduct());

            var product = event.getProduct();
            ProductEventLog log = new ProductEventLog();
            log.setEventType(event.getEventType());
            log.setProductName(product.getName());
            log.setProductDescription(product.getDescription());
            log.setProductPrice(product.getPrice());
            log.setProductQuantity(product.getQuantity());

            eventLogRepository.save(log);
        } else {
            logger.warn("Received null or incomplete ProductEvent: {}", event);
        }
    }
}
