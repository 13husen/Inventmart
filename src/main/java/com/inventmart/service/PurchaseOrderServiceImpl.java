package com.inventmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.inventmart.model.PurchaseOrder;
import com.inventmart.repository.PurchaseOrderRepository;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

@Service("PurchaseOrderService")
public class PurchaseOrderServiceImpl extends BaseCrudService<PurchaseOrder, JpaRepository<PurchaseOrder,Long>> implements PurchaseOrderService {
	
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
		super(purchaseOrderRepository);
		
		this.purchaseOrderRepository = purchaseOrderRepository;
	}

	public PurchaseOrderRepository getPurchaseRepository() {
		return purchaseOrderRepository;
	}

	public void setPurchaseOrderRepository(PurchaseOrderRepository purchaseOrderRepository) {
		this.purchaseOrderRepository = purchaseOrderRepository;
	}
        
	@Override
	public javafx.concurrent.Service<Long> getTotalPo(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) {
		return createService(new Task<Long>() {
			protected Long call() throws Exception {
				return purchaseOrderRepository.getTotalPo();
			};
		}, onSucess, beforeStart);
	}        
        
	@Override
	public javafx.concurrent.Service<Void> setPurchaseOrderStatus(String status, String code,
			EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) {
		return createService(new Task<Void>() {
			protected Void call() throws Exception {
				purchaseOrderRepository.setPurchaseOrderStatus(status, code);
				
				return null;
			};
		}, onSucess, beforeStart);
	}                

	@Override
	public javafx.concurrent.Service<PurchaseOrder> findPurchaseOrderByCode(String code, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) {
		return createService(new Task<PurchaseOrder>() {
			protected PurchaseOrder call() throws Exception {
				return purchaseOrderRepository.findByCode(code);
			};
		}, onSucess, beforeStart);
		
		
	}
	
}
