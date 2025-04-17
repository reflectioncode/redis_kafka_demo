package com.example.redis_kafka_demo.events.product.mapper;

import com.example.redis_kafka_demo.events.product.ProductEventImpl.ProductCreatedEvent;
import com.example.redis_kafka_demo.events.product.ProductEventImpl.ProductRemovedEvent;
import com.example.redis_kafka_demo.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductEventMapper {
    ProductEventMapper INSTANCE = Mappers.getMapper(ProductEventMapper.class);
    ProductCreatedEvent toProductCreatedEvent(Product product);
    ProductRemovedEvent toProductRemovedEvent(Product product);
}
