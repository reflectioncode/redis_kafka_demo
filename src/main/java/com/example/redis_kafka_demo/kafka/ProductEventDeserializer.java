package com.example.redis_kafka_demo.kafka;

import com.example.redis_kafka_demo.kafka.event.ProductEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class ProductEventDeserializer implements Deserializer<ProductEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ProductEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, ProductEvent.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing ProductEvent", e);
        }
    }
}
