package com.example.redis_kafka_demo.repository;

import com.example.redis_kafka_demo.model.entity.ProductEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEventLogRepository extends JpaRepository<ProductEventLog, Long> {
}
