package com.example.redis_kafka_demo.model.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {
    @NotBlank(message = "Имя является обязательным параметром!")
    private String name;
    @NotBlank(message = "Описание является обязательным параметром!")
    private String description;
    @NotNull(message = "Стоимость является обязательным параметром!")
    private BigDecimal price;
    @NotNull(message = "Количество является обязательным параметром!")
    private Integer quantity;
}
