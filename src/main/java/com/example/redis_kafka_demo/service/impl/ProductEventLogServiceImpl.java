package com.example.redis_kafka_demo.service.impl;

import com.example.redis_kafka_demo.dto.ProductEventLogDto;
import com.example.redis_kafka_demo.model.entity.ProductEventLog;
import com.example.redis_kafka_demo.repository.ProductEventLogRepository;
import com.example.redis_kafka_demo.service.ProductEventLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductEventLogServiceImpl implements ProductEventLogService {

    private final ProductEventLogRepository eventLogRepository;

    public ProductEventLogServiceImpl(ProductEventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    public Page<ProductEventLogDto> getEventLogs(int page, int size) {
        return eventLogRepository.findAll(PageRequest.of(page, size))
                .map(this::convertToDto);
    }

    private ProductEventLogDto convertToDto(ProductEventLog log) {
        return new ProductEventLogDto(
                log.getEventType(),
                log.getProductName(),
                log.getProductDescription(),
                log.getProductPrice(),
                log.getProductQuantity(),
                log.getEventTime()
        );
    }
}
