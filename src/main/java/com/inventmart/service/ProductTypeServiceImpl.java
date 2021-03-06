package com.inventmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.inventmart.model.ProductType;
import com.inventmart.repository.ProductTypeRepository;

@Service("productTypeService")
public class ProductTypeServiceImpl extends BaseCrudService<ProductType, JpaRepository<ProductType,Long>> implements ProductTypeService {
	
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	public ProductTypeServiceImpl(ProductTypeRepository repository) {
		super(repository);
		
		this.productTypeRepository = repository;
	}

	public ProductTypeRepository getProductTypeRepository() {
		return productTypeRepository;
	}

	public void setProductTypeRepository(ProductTypeRepository productTypeRepository) {
		this.productTypeRepository = productTypeRepository;
	}
	
}
