package com.inventmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventmart.model.Brand;
/**********************************************/
//   REPOSITORY BRAND (persisting java object ke database)
/**********************************************/
@Repository("brandRepository")
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
