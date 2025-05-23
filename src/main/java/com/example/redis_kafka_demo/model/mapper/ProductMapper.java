package com.example.redis_kafka_demo.model.mapper;
import com.example.redis_kafka_demo.model.dto.request.ProductDto;
import com.example.redis_kafka_demo.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductDto dto);

    //обновление существующего объекта из DTO (используется в сервисе)
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);
}