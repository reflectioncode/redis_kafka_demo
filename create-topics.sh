#!/bin/bash

# Ждём, пока Kafka поднимется
sleep 5

# Создаём топик с 3 партициями
kafka-topics --create \
  --bootstrap-server host.docker.internal:9092 \
  --replication-factor 1 \
  --partitions 3 \
  --topic product-events

# Показываем результат
kafka-topics --describe --topic product-events --bootstrap-server host.docker.internal:9092
