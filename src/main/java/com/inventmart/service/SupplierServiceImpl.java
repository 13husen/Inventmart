package com.inventmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.inventmart.model.Supplier;
import com.inventmart.repository.SupplierRepository;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

@Service("supplierService")
public class SupplierServiceImpl extends BaseCrudService<Supplier, JpaRepository<Supplier,Long>> implements SupplierService {
	
	private SupplierRepository supplierRepository;
	
	@Autowired
	public SupplierServiceImpl(SupplierRepository supplierRepository) {
		super(supplierRepository);
		
		this.supplierRepository = supplierRepository;
	}

	public SupplierRepository getSupplierRepository() {
		return supplierRepository;
	}

	public void setSupplierRepository(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
	}

	@Override
	public javafx.concurrent.Service<Long> getTotalSuppliers(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) {
		return createService(new Task<Long>() {
			protected Long call() throws Exception {
				return supplierRepository.getTotalSuppliers();
			};
		}, onSucess, beforeStart);
	}

	
}
