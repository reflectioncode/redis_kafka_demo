package com.example.redis_kafka_demo.controller;

import com.example.redis_kafka_demo.dto.ProductEventLogDto;
import com.example.redis_kafka_demo.service.ProductEventLogService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class ProductEventLogController {

    private final ProductEventLogService eventLogService;

    public ProductEventLogController(ProductEventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @GetMapping
    public Page<ProductEventLogDto> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return eventLogService.getEventLogs(page, size);
    }
}
