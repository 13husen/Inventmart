package com.inventmart.service;

import com.inventmart.model.PurchaseOrder;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public interface PurchaseOrderService extends IBaseService<PurchaseOrder> {
    Service<PurchaseOrder> findPurchaseOrderByCode(String code, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);
    Service<Void> setPurchaseOrderStatus(String status, String code, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);    
    Service<Long> getTotalPo(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);
	    
}
