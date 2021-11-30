package com.inventmart.service;

import com.inventmart.model.Invoice;
import com.inventmart.repository.InvoiceRepository;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service("InvoiceService")
public class InvoiceServiceImpl extends BaseCrudService<Invoice, JpaRepository<Invoice,Long>> implements InvoiceService {
	
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
		super(invoiceRepository);
		
		this.invoiceRepository = invoiceRepository;
	}

	public InvoiceRepository getInvoiceRepository() {
		return invoiceRepository;
	}

	public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
		this.invoiceRepository = invoiceRepository;
	}

        	@Override
	public javafx.concurrent.Service<Long> getTotalInvoice(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) {
		return createService(new Task<Long>() {
			protected Long call() throws Exception {
				return invoiceRepository.getTotalInvoice();
			};
		}, onSucess, beforeStart);
	}
	
}
