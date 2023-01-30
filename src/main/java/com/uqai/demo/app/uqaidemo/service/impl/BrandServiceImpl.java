package com.uqai.demo.app.uqaidemo.service.impl;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.BrandDto;
import com.uqai.demo.app.uqaidemo.dto.car.BrandMapper;
import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import com.uqai.demo.app.uqaidemo.model.car.Brand;
import com.uqai.demo.app.uqaidemo.repository.BrandRepository;
import com.uqai.demo.app.uqaidemo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private static final String BRAND_NOT_EXISTS = "La marca no existe";

    @Autowired
    public BrandRepository brandRepository;

    @Autowired
    public BrandMapper brandMapper;

    @Override
    public Page<BrandDto> index(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return brandRepository.findAll(pageable).map(brandMapper::toDto);
    }

    @Override
    public List<BrandDto> indexAll() {
        return brandRepository.findAll().stream().map(brandMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BrandDto showBrand(Long brandId) throws ResponseError {
        Optional<Brand> targetBrand = brandRepository.findById(brandId);
        if(targetBrand.isEmpty())
            throw new ResponseError(BRAND_NOT_EXISTS);

        return brandMapper.toDto(targetBrand.get());
    }

    @Override
    public BrandDto createBrand(BrandDto brandDto) {
        return brandMapper.toDto(brandRepository.save(brandMapper.toModel(brandDto)));
    }

    @Override
    public BrandDto updateBrand(Long brandId, BrandDto brandDto) throws ResponseError {
        Optional<Brand> targetBrand = brandRepository.findById(brandId);
        if(targetBrand.isEmpty())
            throw new ResponseError(BRAND_NOT_EXISTS);
        targetBrand.get().setName(brandDto.getName());

        return brandMapper.toDto(brandRepository.save(targetBrand.get()));
    }

    @Override
    public void deleteBrand(Long brandId) throws ResponseError {
        Optional<Brand> targetBrand = brandRepository.findById(brandId);
        if(targetBrand.isEmpty())
            throw new ResponseError(BRAND_NOT_EXISTS);
        targetBrand.get().setStatus(EnumStatus.INA);
        brandRepository.save(targetBrand.get());
    }

    @Override
    public Long countBrands() {
        return brandRepository.countByStatus(EnumStatus.ACT);
    }
}
