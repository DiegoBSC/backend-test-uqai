package com.uqai.demo.app.uqaidemo.service;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.CarDto;
import com.uqai.demo.app.uqaidemo.enums.EnumTypeFile;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CarService {
    Page<CarDto> index(Integer Page, Integer size);
    CarDto showCar(Long carId) throws ResponseError;
    CarDto createCar(CarDto carDto);
    CarDto updateCar(Long carId, CarDto carDto) throws ResponseError;
    void deleteCar(Long carId) throws ResponseError;
    Long countCars();
    ResponseEntity<?> uploadImage(MultipartFile file, Long id, EnumTypeFile typeFile);
    void saveCarsToDatabase(MultipartFile file);
}
