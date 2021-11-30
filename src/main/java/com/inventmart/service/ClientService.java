package com.inventmart.service;

import com.inventmart.model.Client;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public interface ClientService extends IBaseService<Client> {
	
	javafx.concurrent.Service<Long> getTotalClients(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);
}
