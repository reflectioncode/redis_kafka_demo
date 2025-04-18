package com.example.redis_kafka_demo.events.product.productEventImpl;
import com.example.redis_kafka_demo.events.product.ProductEvent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreatedEvent implements ProductEvent {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime createdAt;
}
