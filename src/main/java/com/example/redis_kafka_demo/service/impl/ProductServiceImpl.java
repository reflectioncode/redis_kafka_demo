package com.example.redis_kafka_demo.service.impl;

import com.example.redis_kafka_demo.model.entity.Product;
import com.example.redis_kafka_demo.repository.ProductRepository;
import com.example.redis_kafka_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    @Value("${kafka.topics.product.created}")
    private static String ADDED_PRODUCTS_TOPIC;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
        kafkaTemplate.send(ADDED_PRODUCTS_TOPIC, product);
    }
}

