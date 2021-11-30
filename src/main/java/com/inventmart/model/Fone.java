package com.inventmart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fone")
public class Fone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fone_id")
	private long id;
	
	@Column(name = "code")
	private int code;
	
	@Column(name = "number")
	private String number;

	@ManyToOne
	private Client client;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


	
	
}