package com.uqai.demo.app.uqaidemo.service.impl;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.CatalogDto;
import com.uqai.demo.app.uqaidemo.dto.car.CatalogMapper;
import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import com.uqai.demo.app.uqaidemo.model.car.Catalog;
import com.uqai.demo.app.uqaidemo.repository.CarRepository;
import com.uqai.demo.app.uqaidemo.repository.CatalogRepository;
import com.uqai.demo.app.uqaidemo.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatalogServiceImpl implements CatalogService {

    private static final String CATALOG_NOT_EXISTS = "El catalogo no existe";

    @Autowired
    public CatalogRepository catalogRepository;

    @Autowired
    public CatalogMapper catalogMapper;
    @Autowired
    private CarRepository carRepository;

    @Override
    public Page<CatalogDto> index(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return catalogRepository.findAll(pageable).map(catalogMapper::toDto);
    }

    @Override
    public CatalogDto showCatalog(Long catalogId) throws ResponseError {
        Optional<Catalog> targetCatalog = catalogRepository.findById(catalogId);
        if(targetCatalog.isEmpty())
            throw new ResponseError(CATALOG_NOT_EXISTS);

        return catalogMapper.toDto(targetCatalog.get());
    }

    @Override
    public CatalogDto createCatalog(CatalogDto catalogDto) {
        return catalogMapper.toDto(catalogRepository.save(catalogMapper.toModel(catalogDto)));
    }

    @Override
    public CatalogDto updateCatalog(Long catalogId, CatalogDto catalogDto) throws ResponseError {
        Optional<Catalog> targetCatalog = catalogRepository.findById(catalogId);
        if(targetCatalog.isEmpty())
            throw new ResponseError(CATALOG_NOT_EXISTS);
        targetCatalog.get().setName(catalogDto.getName());

        catalogDto.getCars().forEach(item ->{
            targetCatalog.get().getCars().add(carRepository.findById(item.getId()).get());
        });

        return catalogMapper.toDto(catalogRepository.save(targetCatalog.get()));
    }

    @Override
    public void deleteCatalog(Long catalogId) throws ResponseError {
        Optional<Catalog> targetCatalog = catalogRepository.findById(catalogId);
        if(targetCatalog.isEmpty())
            throw new ResponseError(CATALOG_NOT_EXISTS);
        targetCatalog.get().setStatus(EnumStatus.INA);
        catalogRepository.save(targetCatalog.get());
    }
}
