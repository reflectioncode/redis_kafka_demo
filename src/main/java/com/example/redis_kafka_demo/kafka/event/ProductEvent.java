package com.example.redis_kafka_demo.kafka.event;

import com.example.redis_kafka_demo.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductEvent {
    private String eventType;
    private ProductDto product;

    @JsonCreator
    public ProductEvent(
            @JsonProperty("eventType") String eventType,
            @JsonProperty("product") ProductDto product
    ) {
        this.eventType = eventType;
        this.product = product;
    }

}
