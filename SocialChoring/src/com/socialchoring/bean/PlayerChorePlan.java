package com.socialchoring.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerChorePlan {
	private long id;
	private long player_id;
	private String name;
	private String description;
	private String how_often;
	private String when;
	private String ideal_time;
	private String min_time;
	private String max_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPlayer_id() {
		return this.player_id;
	}

	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
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

	public String getHow_often() {
		return this.how_often;
	}

	public void setHow_often(String how_often) {
		this.how_often = how_often;
	}

	public String getWhen() {
		return this.when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public String getIdeal_time() {
		return this.ideal_time;
	}

	public void setIdeal_time(String ideal_time) {
		this.ideal_time = ideal_time;
	}

	public String getMin_time() {
		return this.min_time;
	}

	public void setMin_time(String min_time) {
		this.min_time = min_time;
	}

	public String getMax_time() {
		return this.max_time;
	}

	public void setMax_time(String max_time) {
		this.max_time = max_time;
	}

}