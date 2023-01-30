package com.uqai.demo.app.uqaidemo.repository;

import com.uqai.demo.app.uqaidemo.model.car.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
}
