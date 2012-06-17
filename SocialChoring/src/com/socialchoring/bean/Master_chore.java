package com.socialchoring.bean;

public class Master_chore {
	private int id;
	private String name;
	private String description;// Description of the chore
	private String ideal_time;
	private String max_time;
	private String min_time;
	private int parent_chore;// null unless this is a sub core.

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdeal_time() {
		return this.ideal_time;
	}

	public void setIdeal_time(String ideal_time) {
		this.ideal_time = ideal_time;
	}

	public String getMax_time() {
		return this.max_time;
	}

	public void setMax_time(String max_time) {
		this.max_time = max_time;
	}

	public String getMin_time() {
		return this.min_time;
	}

	public void setMin_time(String min_time) {
		this.min_time = min_time;
	}

	public int getParent_chore() {
		return this.parent_chore;
	}

	public void setParent_chore(int parent_chore) {
		this.parent_chore = parent_chore;
	}

}