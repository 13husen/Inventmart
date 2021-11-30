package com.inventmart.model.dto;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.inventmart.model.ProductType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductTypeTableDTO extends RecursiveTreeObject<ProductTypeTableDTO> {
	
	private StringProperty name;
	private StringProperty deskripsi;
	private ProductType originalProductType;
	
	public StringProperty getName() {
		return name;
	}
	public void setName(StringProperty name) {
		this.name = name;
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	public StringProperty getDeskripsi() {
		return deskripsi;
	}
	public void setDeskripsi(StringProperty deskripsi) {
		this.deskripsi = deskripsi;
	}
	public void setDeskripsi(String deskripsi) {
		this.deskripsi = new SimpleStringProperty(deskripsi);
	}
	public ProductType getOriginalProductType() {
		return originalProductType;
	}
	public void setOriginalProductType(ProductType productType) {
		this.originalProductType = productType;
	}
	
	
}
