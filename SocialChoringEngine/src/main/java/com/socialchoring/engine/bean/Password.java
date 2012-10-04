package com.socialchoring.engine.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Password {
	private long id;
	private long owner_id;// A foriegn key to either the PARENT or the PLAYER
	private String password;
	private String password_hint;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwner_id() {
		return this.owner_id;
	}

	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword_hint() {
		return this.password_hint;
	}

	public void setPassword_hint(String password_hint) {
		this.password_hint = password_hint;
	}

}