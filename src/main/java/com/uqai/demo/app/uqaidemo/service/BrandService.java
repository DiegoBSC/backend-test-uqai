package com.uqai.demo.app.uqaidemo.service;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.BrandDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {
    Page<BrandDto> index(Integer Page, Integer size);
    List<BrandDto> indexAll();
    BrandDto showBrand(Long brandId) throws ResponseError;
    BrandDto createBrand(BrandDto brandDto);
    BrandDto updateBrand(Long brandId, BrandDto brandDto) throws ResponseError;
    void deleteBrand(Long brandId) throws ResponseError;
    Long countBrands();
}
