package com.inventmart.service;

import com.inventmart.model.Invoice;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public interface InvoiceService extends IBaseService<Invoice> {
	Service<Long> getTotalInvoice(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);
	
}
