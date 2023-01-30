package com.uqai.demo.app.uqaidemo.dto.car;

import com.uqai.demo.app.uqaidemo.model.car.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BrandMapper {
    BrandDto toDto(Brand model);

    Brand toModel(BrandDto dto);
}
