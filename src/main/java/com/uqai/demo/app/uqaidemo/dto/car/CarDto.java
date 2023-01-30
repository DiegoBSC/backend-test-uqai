package com.uqai.demo.app.uqaidemo.dto.car;

import com.uqai.demo.app.uqaidemo.enums.EnumCarStatus;
import com.uqai.demo.app.uqaidemo.model.car.Brand;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CarDto {
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private EnumCarStatus status = EnumCarStatus.NEW;
    private String name;
    @NotNull(message = "Description may not be null")
    private String description;
    @NotNull(message = "Kilometres may not be null")
    private Long kilometres;
    @NotNull(message = "Year may not be null")
    private Long yearCar;
    private BigDecimal price;
    private String image;
    private Brand brand;
}
