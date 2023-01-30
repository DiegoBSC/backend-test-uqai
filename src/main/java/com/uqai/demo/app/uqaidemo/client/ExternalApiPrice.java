package com.uqai.demo.app.uqaidemo.client;

import com.uqai.demo.app.uqaidemo.dto.car.CarPriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "apiPriceFake", url = "https://dummyjson.com/products/")
public interface ExternalApiPrice {
    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    CarPriceDto getPriceProductById(@PathVariable("id") Long postId);
}
