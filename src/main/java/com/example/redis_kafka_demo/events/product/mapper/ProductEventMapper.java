package com.example.redis_kafka_demo.events.product.mapper;

import com.example.redis_kafka_demo.events.product.ProductEventImpl.ProductCreatedEvent;
import com.example.redis_kafka_demo.events.product.ProductEventImpl.ProductRemovedEvent;
import com.example.redis_kafka_demo.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = java.time.LocalDateTime.class)
public interface ProductEventMapper {
    ProductEventMapper INSTANCE = Mappers.getMapper(ProductEventMapper.class);

    ProductCreatedEvent toProductCreatedEvent(Product product);

    //Событие "удаления" содержит поле "Дата удаления", которого нет в сущности
    //При маппинге заполним это поле текущей датой
    @Mapping(target = "removedAt", expression = "java(LocalDateTime.now())")
    ProductRemovedEvent toProductRemovedEvent(Product product);
}
