package com.uqai.demo.app.uqaidemo.controller;

import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.CarDto;
import com.uqai.demo.app.uqaidemo.enums.EnumTypeFile;
import com.uqai.demo.app.uqaidemo.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/car")
public class CarController {
    
    @Autowired
    public CarService carService;

    @PostMapping("/upload-data-cars")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file")MultipartFile file){
        carService.saveCarsToDatabase(file);
        return ResponseEntity
                .ok(Map.of("Message" , " Cars data uploaded and saved to database successfully"));
    }

    @GetMapping
    Page<CarDto> indexCars(@RequestParam Integer page, @RequestParam Integer size){
        return carService.index(page, size);
    }

    @GetMapping("/{carId}")
    CarDto showBrand(@PathVariable Long carId) throws ResponseError {
        return carService.showCar(carId);
    }

    @PostMapping
    CarDto createCar(@Valid @RequestBody CarDto carDto){
        return carService.createCar(carDto);
    }

    @PutMapping("/{carId}")
    CarDto updateBrand(@PathVariable Long carId,
                       @RequestBody CarDto carDto) throws ResponseError {
        return carService.updateCar(carId,carDto);
    }
    @DeleteMapping("/{carId}")
    void deleteBrand(@PathVariable Long carId) throws ResponseError {
        carService.deleteCar(carId);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestPart("file") MultipartFile file, @RequestParam("carId") Long id,
                                    @RequestParam("typeFile") EnumTypeFile typeFile) {
        return carService.uploadImage(file,id,typeFile);
    }

    @GetMapping("/cars-total")
    public Long countTotalCars(){
        return carService.countCars();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
