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

@Entity
@Table(name = "product_transaction")
public class ProductTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_product_transaction")
	private long id;


	@Column(name = "product_id")
	private long productId;
        
	@Column(name = "qty")
	private long qty;

	@Column(name = "note")
	private String note;
        
 	@Column(name = "stock_before")
	private long stockBefore;
        
       	@Column(name = "stock_after")
	private long stockAfter;

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


        public long getQty() {
            return qty;
        }

        public void setQty(long qty) {
            this.qty = qty;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public long getStockBefore() {
            return stockBefore;
        }

        public void setStockBefore(long stockBefore) {
            this.stockBefore = stockBefore;
        }

        public long getStockAfter() {
            return stockAfter;
        }

        public void setStockAfter(long stockAfter) {
            this.stockAfter = stockAfter;
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


        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public PurchaseOrder getPurchaseOrder() {
            return purchaseOrder;
        }

        public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
            this.purchaseOrder = purchaseOrder;
        }

        
        
        
	
	
}
