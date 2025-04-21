package com.example.redis_kafka_demo.service;

import com.example.redis_kafka_demo.model.dto.request.ProductDto;
import com.example.redis_kafka_demo.model.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Product getProductById(Long id);
    Product saveProduct(ProductDto dto) throws JsonProcessingException;
    void deleteProduct(Long id) throws JsonProcessingException;
    Product updateProduct(Long id, ProductDto dto);
}
