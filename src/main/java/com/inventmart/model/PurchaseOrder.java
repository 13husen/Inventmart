package com.inventmart.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_po")
	private long id;

	@Column(name = "code")
	private String code;
        
	@Column(name = "sender_name")
	private String senderName;

	@Column(name = "sender_address")
	private String senderAddress;
        
 	@Column(name = "note")
	private String note;
        
      	@Column(name = "qty")
	private int qty;
        
        @Column(name = "timestamp")
	private String timestamp;
        
        @Column(name = "created_by")
	private int createdBy;
        
        @Column(name = "status")
	private String status;        
        
        
        @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PurchaseOrderDetail purchaseOrderDetail;
        
        @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Product product;
        
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Item> items;
        
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public PurchaseOrderDetail getPurchaseOrderDetail() {
        return purchaseOrderDetail;
    }

    public void setPurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        this.purchaseOrderDetail = purchaseOrderDetail;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}	
    
	@Override
	public String toString() {
		return getCode();
	}
	
	
}
