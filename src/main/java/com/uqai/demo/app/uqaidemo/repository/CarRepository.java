package com.uqai.demo.app.uqaidemo.repository;

import com.uqai.demo.app.uqaidemo.enums.EnumCarStatus;
import com.uqai.demo.app.uqaidemo.model.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
