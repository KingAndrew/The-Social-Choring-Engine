package com.socialchoring.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Parent {
	private long id;
	private String email;
	private String first_name;
	private String last_name;
	private int address_id;// a foriegn key that points to ADDRESS

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getAddress_id() {
		return this.address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

}