package com.example.redis_kafka_demo.service.impl;

import com.example.redis_kafka_demo.events.product.ProductEventImpl.ProductCreatedEvent;
import com.example.redis_kafka_demo.events.product.ProductEvent;
import com.example.redis_kafka_demo.events.product.ProductEventImpl.ProductRemovedEvent;
import com.example.redis_kafka_demo.events.product.mapper.ProductEventMapper;
import com.example.redis_kafka_demo.model.dto.request.ProductDto;
import com.example.redis_kafka_demo.model.entity.Product;
import com.example.redis_kafka_demo.model.mapper.ProductMapper;
import com.example.redis_kafka_demo.repository.ProductRepository;
import com.example.redis_kafka_demo.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;
    @Value("${kafka.topics.product.created}")
    private String added_products_topic;
    @Value("${kafka.topics.product.removed}")
    private String removed_products_topic;
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductServiceImpl(ProductRepository productRepository, KafkaTemplate<String, ProductEvent> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    private void kafkaLogger(CompletableFuture<SendResult<String, ProductEvent>> future){
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                LOGGER.error("Failed to send message", exception.getMessage());
            } else {
                LOGGER.info("Succeful to send message", result.getRecordMetadata().toString());
            }
        });
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        return product;
    }

    public Product saveProduct(ProductDto dto) {
        Product product = ProductMapper.INSTANCE.toEntity(dto);
        productRepository.save(product);

        ProductCreatedEvent productCreatedEvent = ProductEventMapper.INSTANCE.toProductCreatedEvent(product);
        CompletableFuture<SendResult<String, ProductEvent>> future = kafkaTemplate.send(added_products_topic, productCreatedEvent);
        kafkaLogger(future);

        return product;
    }

    public void deleteProduct(Long id) {
        Product deletedProduct = productRepository.findById(id).get();
        productRepository.deleteById(id);

        ProductRemovedEvent productRemovedEvent = ProductEventMapper.INSTANCE.toProductRemovedEvent(deletedProduct);
        CompletableFuture<SendResult<String, ProductEvent>> future = kafkaTemplate.send(removed_products_topic, productRemovedEvent);
        kafkaLogger(future);
    }

    public Product updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        ProductMapper.INSTANCE.updateEntityFromDto(dto, product);

        return productRepository.save(product);
    }
}






