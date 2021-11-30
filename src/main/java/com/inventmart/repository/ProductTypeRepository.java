package com.inventmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventmart.model.ProductType;

@Repository("productTypeRepository")
public interface ProductTypeRepository extends JpaRepository<ProductType, Long>{

}
