/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventmart.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author husen
 */
@Entity
@Table(name = "invoice")
public class Invoice {
    
    
    	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_invoice")
	private long id;

	@Column(name = "invoice_code")
	private String invoiceCode;
        
	@Column(name = "supplier_id")
	private String supplierId;

	@Column(name = "sender_name")
	private String senderName;
        
 	@Column(name = "sender_address")
	private String senderAddress;
        
        
      	@Column(name = "terms")
	private String terms;
        
        @Column(name = "note")
	private String note;
        
        @Column(name = "timestamp")
	private String timestamp;
        
        @Column(name = "created_by")
	private int createdBy;

        @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private PurchaseOrder purchaseOrder;
                
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getInvoiceCode() {
            return invoiceCode;
        }

        public void setInvoiceCode(String invoiceCode) {
            this.invoiceCode = invoiceCode;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getSenderAddress() {
            return senderAddress;
        }

        public void setSenderAddress(String senderAddress) {
            this.senderAddress = senderAddress;
        }


        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public int getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(int createdBy) {
            this.createdBy = createdBy;
        }

        public PurchaseOrder getPurchaseOrder() {
            return purchaseOrder;
        }

        public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
            this.purchaseOrder = purchaseOrder;
        }

        
        
}
