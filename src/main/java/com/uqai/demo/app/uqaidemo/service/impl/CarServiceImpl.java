package com.uqai.demo.app.uqaidemo.service.impl;

import com.uqai.demo.app.uqaidemo.client.ExternalApiPrice;
import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.car.BrandMapper;
import com.uqai.demo.app.uqaidemo.dto.car.CarDto;
import com.uqai.demo.app.uqaidemo.dto.car.CarMapper;
import com.uqai.demo.app.uqaidemo.dto.car.CarPriceDto;
import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import com.uqai.demo.app.uqaidemo.enums.EnumTypeFile;
import com.uqai.demo.app.uqaidemo.model.car.Brand;
import com.uqai.demo.app.uqaidemo.model.car.Car;
import com.uqai.demo.app.uqaidemo.model.car.Catalog;
import com.uqai.demo.app.uqaidemo.repository.BrandRepository;
import com.uqai.demo.app.uqaidemo.repository.CarRepository;
import com.uqai.demo.app.uqaidemo.repository.CatalogRepository;
import com.uqai.demo.app.uqaidemo.service.CarService;
import com.uqai.demo.app.uqaidemo.service.UploadFileService;
import com.uqai.demo.app.uqaidemo.util.CustomResponse;
import com.uqai.demo.app.uqaidemo.util.ExcelUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    private static final String CAR_NOT_EXISTS = "El auto no existe";

    @Autowired
    public CarRepository carRepository;

    @Autowired
    public BrandRepository brandRepository;

    @Autowired
    public CatalogRepository catalogRepository;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    public CarMapper carMapper;

    @Autowired
    public ExternalApiPrice externalApiPrice;

    @Override
    public Page<CarDto> index(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.findAll(pageable).map(carMapper::toDto);
    }

    @Override
    public CarDto showCar(Long carId) throws ResponseError {
        Optional<Car> targetCar = carRepository.findById(carId);
        if(targetCar.isEmpty())
            throw new ResponseError(CAR_NOT_EXISTS);

        return carMapper.toDto(targetCar.get());
    }

    @Override
    public CarDto createCar(CarDto carDto) {
        int id = (int) (Math.random() * 100) + 1;
        CarPriceDto carPriceDto =  externalApiPrice.getPriceProductById(Long.valueOf(id));
        Car model = carMapper.toModel(carDto);
        model.setPrice(carPriceDto.getPrice().multiply(BigDecimal.valueOf(1000)));
        return carMapper.toDto(carRepository.save(model));
    }

    @Override
    @Transactional
    public CarDto updateCar(Long carId, CarDto carDto) throws ResponseError {
        Optional<Car> targetCar = carRepository.findById(carId);
        if(targetCar.isEmpty())
            throw new ResponseError(CAR_NOT_EXISTS);

        targetCar.get().setName(carDto.getName());
        targetCar.get().setDescription(carDto.getDescription());
        targetCar.get().setKilometres(carDto.getKilometres());
        targetCar.get().setYearCar(carDto.getYearCar());
        targetCar.get().setBrand(assignBrand(carDto.getBrand()));
        targetCar.get().setStatus(carDto.getStatus());
        return carMapper.toDto(carRepository.save(targetCar.get()));
    }

    @Override
    public void deleteCar(Long carId) throws ResponseError {
        Optional<Car> targetCar = carRepository.findById(carId);
        if(targetCar.isEmpty())
            throw new ResponseError(CAR_NOT_EXISTS);
        List<Catalog> listCatalogs = targetCar.get().getCatalogs();
        listCatalogs.forEach(element ->{
            element.getCars().remove(targetCar.get());
        });
        catalogRepository.saveAll(listCatalogs);
        carRepository.deleteById(carId);
    }

    @Override
    public Long countCars() {
        return  carRepository.count();
    }

    private Brand assignBrand(Brand brand){

        Optional<Brand> brandOptional = findBrandByName(brand.getName());

        return brandOptional.orElseGet(() -> createNewBrand(brand.getName()));
    }
    private Optional<Brand> findBrandByName(String brandName){
        return brandRepository.findByNameIgnoreCase(brandName);
    }

    private Brand createNewBrand(String brandName){
        Brand brand = Brand.builder()
                .name(brandName)
                .status(EnumStatus.ACT)
                .build();
        return brandRepository.save(brand);
    }
    @Override
    public ResponseEntity<?> uploadImage(MultipartFile file, Long carId, EnumTypeFile typeFile) {
        Optional<Car> car = carRepository.findById(carId);
        String fileNameDb = null;

        if (!car.isEmpty()) {
            String oldFileName = car.get().getImage();
            fileNameDb = uploadFileService.uploadImageCloud(file, typeFile.name());
            car.get().setImage(fileNameDb);
            if(oldFileName != null && !oldFileName.isBlank())
                uploadFileService.deleteImageOldCloud(oldFileName, typeFile.name());

            carRepository.save(car.get());
        }
        return new ResponseEntity<>(CustomResponse
                .builder()
                .type("OK")
                .message("La imagen fue correctamente cargada: ")
                .fieldUpdate(fileNameDb)
                .build(), HttpStatus.CREATED);
    }

    public void saveCarsToDatabase(MultipartFile file){
        if(ExcelUploadUtil.isValidExcelFile(file)){
            try {
                List<Car> cars = ExcelUploadUtil.getCarsDataFromExcel(file.getInputStream());
                cars.forEach(car -> {
                    car.setBrand(assignBrand(car.getBrand()));
                });
                this.carRepository.saveAll(cars);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }
}
