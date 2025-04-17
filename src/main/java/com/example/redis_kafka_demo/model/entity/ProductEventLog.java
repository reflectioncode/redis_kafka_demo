package com.example.redis_kafka_demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_event_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer productQuantity;

    private LocalDateTime eventTime = LocalDateTime.now();
}
