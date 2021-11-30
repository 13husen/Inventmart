package com.inventmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.inventmart.model.Product;
import com.inventmart.repository.ProductRepository;
import java.util.List;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

@Service("productService")
public class ProductServiceImpl extends BaseCrudService<Product, JpaRepository<Product,Long>> implements ProductService {
	
	private ProductRepository productRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		super(productRepository);
		
		this.productRepository = productRepository;
	}

	public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}        

	@Override
	public javafx.concurrent.Service<List<Product>> findBySupplierId(long supplierId, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) {
		return createService(new Task<List<Product>>() {
			protected List<Product> call() throws Exception {
				return productRepository.findBySupplierId(supplierId);
			};
		}, onSucess, beforeStart);
		
		
	}
        
	@Override
	public javafx.concurrent.Service<Void> setProductQuantity(double stock, long id,
			EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) {
		return createService(new Task<Void>() {
			protected Void call() throws Exception {
				productRepository.setProductQuantity(stock, id);
				
				return null;
			};
		}, onSucess, beforeStart);
	}        
        
	@Override
	public javafx.concurrent.Service<Long> getTotalProduct(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) {
		return createService(new Task<Long>() {
			protected Long call() throws Exception {
				return productRepository.getTotalProduct();
			};
		}, onSucess, beforeStart);
	}        
        
	
}
