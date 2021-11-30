package com.inventmart.service;

import com.inventmart.model.Product;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public interface ProductService extends IBaseService<Product> {
    
    	Service<List<Product>> findBySupplierId(long supplierId, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);
	Service<Void> setProductQuantity(double stock, long id, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);
        Service<Long> getTotalProduct(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);
}
