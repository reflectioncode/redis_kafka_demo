package com.example.redis_kafka_demo.service.impl;

import com.example.redis_kafka_demo.events.product.ProductEventImpl.ProductCreatedEvent;
import com.example.redis_kafka_demo.events.product.ProductEvent;
import com.example.redis_kafka_demo.events.product.mapper.ProductEventMapper;
import com.example.redis_kafka_demo.model.dto.request.ProductCreateRequestDto;
import com.example.redis_kafka_demo.model.entity.Product;
import com.example.redis_kafka_demo.model.mapper.ProductMapper;
import com.example.redis_kafka_demo.repository.ProductRepository;
import com.example.redis_kafka_demo.service.ProductService;

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
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductServiceImpl(ProductRepository productRepository, KafkaTemplate<String, ProductEvent> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(ProductCreateRequestDto dto) {
        Product product = ProductMapper.INSTANCE.toEntity(dto);
        productRepository.save(product);

        //подготовим и отправим сообщение
        ProductCreatedEvent productCreatedEvent = ProductEventMapper.INSTANCE.toProductCreatedEvent(product);
        CompletableFuture<SendResult<String, ProductEvent>> future = kafkaTemplate.send(added_products_topic, productCreatedEvent);

        //Залогируем результат отправки сообщения
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                LOGGER.error("Failed to send message", exception.getMessage());
            } else {
                LOGGER.info("Succeful to send message", result.getRecordMetadata().toString());
            }
        });
        return product;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}






