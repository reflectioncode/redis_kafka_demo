package com.example.redis_kafka_demo.controller;

import com.example.redis_kafka_demo.model.entity.Product;
import com.example.redis_kafka_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    @Value("${kafka.topics.product}")
    private static String TOPIC;

    @GetMapping
    public List<Product> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        kafkaTemplate.send(TOPIC, product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
