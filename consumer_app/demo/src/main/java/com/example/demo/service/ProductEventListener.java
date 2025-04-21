package com.example.demo.service;

import com.example.demo.events.product.ProductEvent;
import com.example.demo.events.product.productEventImpl.ProductCreatedEvent;
import com.example.demo.events.product.productEventImpl.ProductRemovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventListener.class);

    @KafkaListener(topics = "products-event-topic", groupId = "product_group")
    public void listen(ProductEvent event) {
        if (event instanceof ProductCreatedEvent) {
            handleProductCreated((ProductCreatedEvent) event);
        } else if (event instanceof ProductRemovedEvent) {
            handleProductRemoved((ProductRemovedEvent) event);
        }
    }

    private void handleProductCreated(ProductCreatedEvent event) {
        LOGGER.info("Received Product Created Event: {}", event);
        // Здесь вы можете добавить дополнительную логику обработки события
    }

    private void handleProductRemoved(ProductRemovedEvent event) {
        LOGGER.info("Received Product Removed Event: {}", event);
        // Здесь вы можете добавить дополнительную логику обработки события
    }
}