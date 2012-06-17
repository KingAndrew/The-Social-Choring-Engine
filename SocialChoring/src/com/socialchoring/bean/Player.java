package com.socialchoring.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Player {
	private int id;

	private String first_name;// I would like to make the first name unique for
								// each parent_id.
	private String earnings;// The amount earned from winning.
	private int champion_count;// When a player earns more than any of his
								// friends for the day that player gets one
								// additional award_count.
	private int parent_id;
	private String date_added;
	private String date_validated;
	private int version;// Version of the table

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getEarnings() {
		return this.earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

	public int getChampion_count() {
		return this.champion_count;
	}

	public void setChampion_count(int champion_count) {
		this.champion_count = champion_count;
	}

	public int getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getDate_added() {
		return this.date_added;
	}

	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}

	public String getDate_validated() {
		return this.date_validated;
	}

	public void setDate_validated(String date_validated) {
		this.date_validated = date_validated;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}