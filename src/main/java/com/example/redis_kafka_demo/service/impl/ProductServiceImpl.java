package com.example.redis_kafka_demo.service.impl;

import com.example.redis_kafka_demo.dto.ProductDto;
import com.example.redis_kafka_demo.kafka.event.ProductEvent;
import com.example.redis_kafka_demo.kafka.producer.ProductProducer;
import com.example.redis_kafka_demo.model.entity.Product;
import com.example.redis_kafka_demo.repository.ProductRepository;
import com.example.redis_kafka_demo.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Product> redisTemplate;
    private final ProductProducer productProducer;

    public ProductServiceImpl(ProductRepository productRepository,
                              RedisTemplate<String, Product> redisTemplate,
                              ProductProducer productProducer) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.productProducer = productProducer;
    }

    public Product createProduct(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        Product saved = productRepository.save(product);

        ProductEvent event = new ProductEvent("CREATED", dto);
        productProducer.sendProductEvent(event);

        return saved;
    }

    public Product getProduct(Long id) {
        String key = "product::" + id;
        Product cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return cached;
        }
        Product product = productRepository.findById(id).orElseThrow();
        redisTemplate.opsForValue().set(key, product);
        return product;
    }

    public Product updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        Product updated = productRepository.save(product);

        redisTemplate.delete("product::" + id);

        ProductEvent event = new ProductEvent("UPDATED", dto);
        productProducer.sendProductEvent(event);

        return updated;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        redisTemplate.delete("product::" + id);

        ProductEvent event = new ProductEvent("DELETED", new ProductDto());
        productProducer.sendProductEvent(event);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}

