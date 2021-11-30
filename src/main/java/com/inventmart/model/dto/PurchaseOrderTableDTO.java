package com.inventmart.model.dto;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.inventmart.model.PurchaseOrder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PurchaseOrderTableDTO extends RecursiveTreeObject<PurchaseOrderTableDTO> {
	
	private StringProperty code;
	private StringProperty senderName;
	private StringProperty senderAddress;
	private StringProperty note;
//	private StringProperty idProduct;
	private StringProperty qty;
        private StringProperty status;        
	private StringProperty timestamp;
     	private PurchaseOrder originalPurchaseOrder;          
        
        
	public PurchaseOrderTableDTO( String code, String sender_name, String sender_address, 
                String note, int id_product, int qty, 
                String timestamp , String status, PurchaseOrder originalPurchaseOrder) {
		this.code = new SimpleStringProperty(code);
		this.senderName = new SimpleStringProperty(sender_name);
		this.senderAddress = new SimpleStringProperty(sender_address);
		this.note = new SimpleStringProperty(note);
//		this.idProduct = new SimpleStringProperty(id_product + "");
                this.qty = new SimpleStringProperty(qty + "");
                this.status = new SimpleStringProperty(status);                
		this.timestamp = new SimpleStringProperty(timestamp);
		this.originalPurchaseOrder = originalPurchaseOrder;
	}
	
	public PurchaseOrderTableDTO() {
		
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
        
	public StringProperty getSenderName() {
		return senderName;
	}
        
	public void setSenderName(String senderName) {
		this.senderName = new SimpleStringProperty(senderName);
	}
        
	public void setSenderName(StringProperty senderName) {
		this.senderName = senderName;
	}        

	public StringProperty getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(StringProperty senderAddress) {
		this.senderAddress = senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = new SimpleStringProperty(senderAddress);
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

//	public void setIdProduct(long idProduct) {
//		this.idProduct = new SimpleStringProperty(Long.toString(idProduct));
//	}
//	public void setIdProduct(StringProperty idProduct) {
//		this.idProduct = idProduct;
//	} 
//        
//	public StringProperty getIdProduct() {
//		return idProduct;
//	}
	
	public void setQty(long qty) {
		this.qty = new SimpleStringProperty(Long.toString(qty));
	}

	public void setQty(StringProperty qty) {
		this.qty = qty;
	} 
        
	public StringProperty getQty() {
		return qty;
	}
        
	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}

	public void setStatus(StringProperty status) {
		this.status = status;
	} 
        
	public StringProperty getStatus() {
		return status;
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
        

	public PurchaseOrder getOriginalPurchaseOrder() {
		return originalPurchaseOrder;
	}

	public void setOriginalPurchaseOrder(PurchaseOrder originalPurchaseOrder) {
		this.originalPurchaseOrder = originalPurchaseOrder;
	}
	
}
