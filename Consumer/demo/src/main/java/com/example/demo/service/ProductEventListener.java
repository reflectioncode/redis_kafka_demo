package com.example.demo.service;

import com.example.demo.events.product.ProductEvent;
import com.example.demo.events.product.productEventImpl.ProductCreatedEvent;
import com.example.demo.events.product.productEventImpl.ProductRemovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductEventListener {

    @KafkaListener(topics = "your_topic_name", groupId = "product-event-group")
    public void listen(ProductEvent event) {
        if (event instanceof ProductCreatedEvent) {
            ProductCreatedEvent createdEvent = (ProductCreatedEvent) event;
            log.info("Received Product Created Event: {}", createdEvent);
            // Здесь вы можете добавить дополнительную логику обработки события
        } else if (event instanceof ProductRemovedEvent) {
            ProductRemovedEvent removedEvent = (ProductRemovedEvent) event;
            log.info("Received Product Removed Event: {}", removedEvent);
            // Здесь вы можете добавить дополнительную логику обработки события
        } else {
            log.warn("Received unknown event type: {}", event.getClass().getName());
        }
    }
}