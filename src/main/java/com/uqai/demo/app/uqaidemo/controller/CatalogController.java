package com.uqai.demo.app.uqaidemo.controller;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.CatalogDto;
import com.uqai.demo.app.uqaidemo.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/catalog")
public class CatalogController {
    
    @Autowired
    public CatalogService catalogService;

    @GetMapping
    Page<CatalogDto> indexCatalog(@RequestParam Integer page, @RequestParam Integer size){
        return catalogService.index(page, size);
    }

    @GetMapping("/{catalogId}")
    CatalogDto showBrand(@PathVariable Long catalogId) throws ResponseError {
        return catalogService.showCatalog(catalogId);
    }

    @PostMapping
    CatalogDto createCatalog(@RequestBody CatalogDto catalogDto){
        return catalogService.createCatalog(catalogDto);
    }

    @PutMapping("/{catalogId}")
    CatalogDto updateCatalog(@PathVariable Long catalogId,
                       @RequestBody CatalogDto catalogDto) throws ResponseError {
        return catalogService.updateCatalog(catalogId, catalogDto);
    }
    @DeleteMapping("/{catalogId}")
    void deleteBrand(@PathVariable Long catalogId) throws ResponseError {
        catalogService.deleteCatalog(catalogId);
    }
}
