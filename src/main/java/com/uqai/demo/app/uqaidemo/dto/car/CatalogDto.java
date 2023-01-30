package com.uqai.demo.app.uqaidemo.dto.car;

import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import com.uqai.demo.app.uqaidemo.model.car.Car;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CatalogDto {
    private Long id;
    private String name;
    private EnumStatus status = EnumStatus.ACT;
    private Set<Car> cars;
}
