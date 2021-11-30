/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventmart.model.dto;

import com.inventmart.model.Invoice;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author husen
 */
public class InvoiceTableDTO extends RecursiveTreeObject<InvoiceTableDTO>{
    	private StringProperty invoiceCode;
        private StringProperty purchaseOrderCode;
	private StringProperty supplierId;
	private StringProperty senderName;
	private StringProperty senderAddress;
	private StringProperty idPo;
	private StringProperty terms;
	private StringProperty note;
	private StringProperty timestamp;
	private StringProperty createdBy;        
     	private Invoice originalInvoice;          
        
        
	public InvoiceTableDTO( String invoice_code, String supplier_id, String sender_name, 
                String sender_address, int id_po, String terms, 
                String note , String timestamp, String created_by, String purchaseOrderCode, Invoice original_invoice) {
		this.invoiceCode = new SimpleStringProperty(invoice_code);
		this.supplierId = new SimpleStringProperty(supplier_id);
                this.senderName = new SimpleStringProperty(sender_name);
		this.senderAddress = new SimpleStringProperty(sender_address);
		this.purchaseOrderCode = new SimpleStringProperty(purchaseOrderCode);
		this.terms = new SimpleStringProperty(terms);
                this.note = new SimpleStringProperty(note + "");
		this.timestamp = new SimpleStringProperty(timestamp);
		this.createdBy = new SimpleStringProperty(created_by);                
		this.originalInvoice = original_invoice;
	}
        
        public InvoiceTableDTO() {
		
	}

	
        public StringProperty getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(StringProperty invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
        
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = new SimpleStringProperty(invoiceCode);
	}        
	
	public StringProperty getSupplierId() {
		return supplierId;
	}
        
	public void setSupplierId(String supplierId) {
		this.supplierId = new SimpleStringProperty(supplierId);
	}

	public StringProperty getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(StringProperty senderAddress) {
		this.senderAddress = senderAddress;
	}
        
	public StringProperty getSenderName() {
		return senderAddress;
	}

	public void setSenderName(StringProperty senderName) {
		this.senderName = senderName;
	}        
	
	public void setIdPo(int idPo) {
		this.idPo = new SimpleStringProperty(Integer.toString(idPo));
	}
	
	public StringProperty getIdPo() {
		return idPo;
	}

	public void setTerms(String terms) {
		this.terms = new SimpleStringProperty(terms);
	}

	public StringProperty getTerms() {
		return terms;
	}
        
	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = new SimpleStringProperty(purchaseOrderCode);
	}

	public StringProperty getPurchaseOrderCode() {
		return purchaseOrderCode;
	}        
        
	public void setNote(String note) {
		this.note = new SimpleStringProperty(note);
	}        
        
	public StringProperty getNote() {
		return note;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = new SimpleStringProperty(timestamp);
	}

	public StringProperty getTimestamp() {
		return timestamp;
	}
        
	public StringProperty getCreatedBy() {
		return createdBy;
	}

	public void setTimestamp(StringProperty createdBy) {
		this.createdBy = createdBy;
	}
	

	public Invoice getOriginalInvoice() {
		return originalInvoice;
	}

	public void setOriginalInvoice(Invoice originalInvoice) {
		this.originalInvoice = originalInvoice;
	}
}
