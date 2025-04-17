package com.example.redis_kafka_demo.service;

import com.example.redis_kafka_demo.model.dto.request.ProductDto;
import com.example.redis_kafka_demo.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Product getProductById(Long id);
    Product saveProduct(ProductDto dto);
    void deleteProduct(Long id);
    Product updateProduct(Long id, ProductDto dto);
}
