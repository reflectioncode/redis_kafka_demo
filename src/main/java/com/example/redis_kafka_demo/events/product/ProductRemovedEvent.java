package com.example.redis_kafka_demo.events.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRemovedEvent implements ProductEvent {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
