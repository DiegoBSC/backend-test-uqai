package com.uqai.demo.app.uqaidemo.dto.car;

import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDto {
    private Long id;
    private String name;
    private EnumStatus status = EnumStatus.ACT;
}
