package com.inventmart.model.dto;

import com.inventmart.model.ProductTransaction;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductTransactionTableDTO extends RecursiveTreeObject<ProductTransactionTableDTO> {
	
	private StringProperty idPo;
        private StringProperty code;
	private StringProperty qty;
	private StringProperty note;
	private StringProperty stockBefore;
	private StringProperty stockAfter;        
	private StringProperty timestamp;
     	private ProductTransaction originalProductTransaction;          
        
        
	public ProductTransactionTableDTO( String id_po, String code, String id_product, String qty, 
                String note, int stock_before, int stock_after, 
                String timestamp ,ProductTransaction originalProductTransaction) {
		this.idPo = new SimpleStringProperty(id_po);
                this.code = new SimpleStringProperty(code);
		this.qty = new SimpleStringProperty(qty+"");
		this.note = new SimpleStringProperty(note);
		this.stockBefore = new SimpleStringProperty(stock_before + "");
                this.stockAfter = new SimpleStringProperty(stock_after + "");
		this.timestamp = new SimpleStringProperty(timestamp);
		this.originalProductTransaction = originalProductTransaction;
	}
	
	public ProductTransactionTableDTO() {
		
	}

	
        public StringProperty getIdPo() {
		return idPo;
	}

	public void setIdPo(StringProperty idPo) {
		this.idPo = idPo;
	}
        
 
	public void setIdPo(long idPo) {
                this.idPo = new SimpleStringProperty(Long.toString(idPo));
	}	
        
        public StringProperty getCode() {
		return code;
	}

	public void setCode(StringProperty code) {
		this.code = code;
	}
        
 
	public void setCode(String code) {
                this.code = new SimpleStringProperty(code);
	}	        
        
	public StringProperty getQty() {
		return qty;
	}

	public void setQty(StringProperty qty) {
		this.qty = qty;
	}
	public void setQty(long qty) {
		this.qty = new SimpleStringProperty(Long.toString(qty));
	}
        
	public void setNote(String note) {
		this.note = new SimpleStringProperty(note);
	}
	public void setNote(StringProperty note) {
		this.note = note;
	}     
        
	public StringProperty getNote() {
		return note;
	}

	public void setStockBefore(long stockBefore) {
		this.stockBefore = new SimpleStringProperty(Long.toString(stockBefore));
	}
	public void setStockBefore(StringProperty stockBefore) {
		this.stockBefore = stockBefore;
	} 
        
	public StringProperty getStockBefore() {
		return stockBefore;
	}
        
	public void setStockAfter(long stockAfter) {
		this.stockAfter = new SimpleStringProperty(Long.toString(stockAfter));
	}
	public void setStockAfter(StringProperty stockAfter) {
		this.stockAfter = stockBefore;
	} 
        
	public StringProperty getStockAfter() {
		return stockAfter;
	}
        
	public StringProperty getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(StringProperty timestamp) {
		this.timestamp = timestamp;
	}
        
	public void setTimestamp(String timestamp) {
		this.timestamp = new SimpleStringProperty(timestamp);
	}
        

	public ProductTransaction getOriginalProductTransaction() {
		return originalProductTransaction;
	}

	public void setOriginalProductTransaction(ProductTransaction originalProductTransaction) {
		this.originalProductTransaction = originalProductTransaction;
	}
	
}
