package com.uqai.demo.app.uqaidemo.service;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.CatalogDto;
import org.springframework.data.domain.Page;

public interface CatalogService {
    Page<CatalogDto> index(Integer Page, Integer size);
    CatalogDto showCatalog(Long catalogId) throws ResponseError;
    CatalogDto createCatalog(CatalogDto catalogDto);
    CatalogDto updateCatalog(Long catalogId, CatalogDto catalogDto) throws ResponseError;
    void deleteCatalog(Long catalogId) throws ResponseError;
}
