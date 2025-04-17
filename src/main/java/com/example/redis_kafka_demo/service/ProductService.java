package com.example.redis_kafka_demo.service;

import com.example.redis_kafka_demo.model.dto.request.ProductCreateRequestDto;
import com.example.redis_kafka_demo.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Optional<Product> getProductById(Long id);
    Product saveProduct(ProductCreateRequestDto dto);
    void deleteProduct(Long id);
}
