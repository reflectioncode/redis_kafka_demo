package com.example.redis_kafka_demo.service;

import com.example.redis_kafka_demo.dto.ProductDto;
import com.example.redis_kafka_demo.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Product createProduct(ProductDto dto);

    Product getProduct(Long id);

    Product updateProduct(Long id, ProductDto dto);

    void deleteProduct(Long id);

    Page<Product> getAllProducts(Pageable pageable);
}
