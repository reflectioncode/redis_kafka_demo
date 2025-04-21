package com.example.redis_kafka_demo.service.impl;

import com.example.redis_kafka_demo.events.product.productEventImpl.ProductCreatedEvent;
import com.example.redis_kafka_demo.events.product.ProductEvent;
import com.example.redis_kafka_demo.events.product.productEventImpl.ProductRemovedEvent;
import com.example.redis_kafka_demo.events.product.mapper.ProductEventMapper;
import com.example.redis_kafka_demo.model.dto.request.ProductDto;
import com.example.redis_kafka_demo.model.entity.Product;
import com.example.redis_kafka_demo.model.mapper.ProductMapper;
import com.example.redis_kafka_demo.repository.ProductRepository;
import com.example.redis_kafka_demo.service.ProductService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.topics.product}")
    private String products_topic;
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ProductServiceImpl(ProductRepository productRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    private void kafkaLogger(CompletableFuture<SendResult<String, String>> future){
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

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        return product;
    }

    public Product saveProduct(ProductDto dto) throws JsonProcessingException {
        Product product = ProductMapper.INSTANCE.toEntity(dto);
        productRepository.save(product);

        ProductCreatedEvent productCreatedEvent = ProductEventMapper.INSTANCE.toProductCreatedEvent(product);
        //асинхронно отправим сообщение в кафку
        //т.е. код продолжит своё выполнения, не дожидаясь получения уведомления о получении
        //при отправке сообщения не указан ключ => сообщение будет попадать в случайную доступную партицию
        String jsonMessage = objectMapper.writeValueAsString(productCreatedEvent);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(products_topic, jsonMessage);
        kafkaLogger(future);

        return product;
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) throws JsonProcessingException {
        Product deletedProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        productRepository.delete(deletedProduct);

        ProductRemovedEvent productRemovedEvent = ProductEventMapper.INSTANCE.toProductRemovedEvent(deletedProduct);
        productRemovedEvent jsonMessage = objectMapper.w
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(products_topic, jsonMessage);
        kafkaLogger(future);
    }

    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        ProductMapper.INSTANCE.updateEntityFromDto(dto, product);

        return productRepository.save(product);
    }
}






