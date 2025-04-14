package com.example.redis_kafka_demo.service;

import com.example.redis_kafka_demo.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> getAllProducts();
    public Optional<Product> getProductById(Long id);
    public void saveProduct(Product product);
}
