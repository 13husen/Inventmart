package com.inventmart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_type")
public class ProductType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_product_type")
	private long id;

	@Column(name = "name")
	private String name;
        
	@Column(name = "deskripsi")
	private String deskripsi;        
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }           
	
	@Override
	public String toString() {
		return getName();
	}
	
	
}
