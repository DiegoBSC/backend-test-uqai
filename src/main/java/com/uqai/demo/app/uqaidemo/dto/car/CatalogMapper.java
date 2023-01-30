package com.uqai.demo.app.uqaidemo.dto.car;

import com.uqai.demo.app.uqaidemo.model.car.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CatalogMapper {
    CatalogDto toDto(Catalog model);

    Catalog toModel(CatalogDto dto);
}
