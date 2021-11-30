package com.inventmart.service;

import com.inventmart.model.ProductTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.inventmart.repository.ProductTransactionRepository;

@Service("ProductTransactionService")
public class ProductTransactionServiceImpl extends BaseCrudService<ProductTransaction, JpaRepository<ProductTransaction,Long>> implements ProductTransactionService {
	
	private ProductTransactionRepository productTransactionRepository;
	
	@Autowired
	public ProductTransactionServiceImpl(ProductTransactionRepository productTransactionRepository) {
		super(productTransactionRepository);
		
		this.productTransactionRepository = productTransactionRepository;
	}

	public ProductTransactionRepository getProductTransactionRepository() {
		return productTransactionRepository;
	}

	public void setProductTransactionRepository(ProductTransactionRepository productTransactionRepository) {
		this.productTransactionRepository = productTransactionRepository;
	}

	
}
