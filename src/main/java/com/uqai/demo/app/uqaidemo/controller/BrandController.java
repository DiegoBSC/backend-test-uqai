package com.uqai.demo.app.uqaidemo.controller;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.BrandDto;
import com.uqai.demo.app.uqaidemo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/brand")
public class BrandController {
    
    @Autowired
    public BrandService brandService;

    @GetMapping
    Page<BrandDto> indexBrands(@RequestParam Integer page, @RequestParam Integer size){
        return brandService.index(page, size);
    }

    @GetMapping("/all")
    List<BrandDto> indexBrandsAll(){
        return brandService.indexAll();
    }

    @GetMapping("/{brandId}")
    BrandDto showBrand(@PathVariable Long brandId) throws ResponseError {
        return brandService.showBrand(brandId);
    }

    @PostMapping
    BrandDto createBrand(@RequestBody BrandDto brandDto){
        return brandService.createBrand(brandDto);
    }

    @PutMapping("/{brandId}")
    BrandDto updateBrand(@PathVariable Long brandId,
                       @RequestBody BrandDto brandDto) throws ResponseError {
        return brandService.updateBrand(brandId,brandDto);
    }
    @GetMapping("/brands-total")
    public Long countTotalBrands(){
        return brandService.countBrands();
    }
    @DeleteMapping("/{brandId}")
    void deleteBrand(@PathVariable Long brandId) throws ResponseError {
        brandService.deleteBrand(brandId);
    }
}
