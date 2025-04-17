package com.example.redis_kafka_demo.events.product.ProductEventImpl;

import com.example.redis_kafka_demo.events.product.ProductEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRemovedEvent implements ProductEvent {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime removedAt;
}
