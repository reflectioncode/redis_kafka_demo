package com.example.redis_kafka_demo.kafka.event;

import com.example.redis_kafka_demo.dto.ProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductEvent {
    private String eventType; // CREATED, UPDATED, DELETED
    private ProductDto product;

    public ProductEvent(String created, ProductDto dto) {
    }

    // Getters и Setters
}
