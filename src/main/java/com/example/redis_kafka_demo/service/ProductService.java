package com.example.redis_kafka_demo.service;

import com.example.redis_kafka_demo.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Page<Product> getAllProducts(Pageable pageable);
    public Optional<Product> getProductById(Long id);
    public void saveProduct(Product product);
}
