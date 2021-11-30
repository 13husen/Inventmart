package com.inventmart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_order_detail")
public class PurchaseOrderDetail {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_purchase_order_detail")
    private long id;

    @Column(name = "po_code")
    private String poCode;

    @Column(name = "id_product")
    private long idProduct;

    @Column(name = "qty")
    private long qty;

    @Column(name = "note")
    private String note;

    @Column(name = "timestamp")
    private String timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }


    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    
	
}
