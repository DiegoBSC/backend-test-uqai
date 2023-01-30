package com.uqai.demo.app.uqaidemo.repository;

import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import com.uqai.demo.app.uqaidemo.model.car.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository  extends JpaRepository<Brand, Long> {
    Optional<Brand> findByNameIgnoreCase(String name);

    Long countByStatus(EnumStatus status);
}
