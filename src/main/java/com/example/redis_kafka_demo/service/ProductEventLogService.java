package com.example.redis_kafka_demo.service;

import com.example.redis_kafka_demo.dto.ProductEventLogDto;
import org.springframework.data.domain.Page;

public interface ProductEventLogService {
    Page<ProductEventLogDto> getEventLogs(int page, int size);
}
