package com.inventmart.service;

import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

// event handler service (untuk operasi dasar)
public interface IBaseService<T> {
	public Service<T> save(T obj, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) throws Exception;
	public Service<List<T>> findAll(EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart);
	public Service<Void> delete(long id, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) throws Exception;
	public Service<T> find(long id, EventHandler<WorkerStateEvent> onSucess, EventHandler<WorkerStateEvent> beforeStart) throws Exception;
	
	public void onClose();
}
