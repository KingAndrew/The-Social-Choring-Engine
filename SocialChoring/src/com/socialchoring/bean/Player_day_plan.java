package com.socialchoring.bean;

public class Player_day_plan {
	private int id;
	private int player_id;
	private String morning_start;
	private String morning_end;
	private String day_start;
	private String day_end;
	private String evening_start;
	private String evening_end;
	private String night_start;
	private String night_end;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayer_id() {
		return this.player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getMorning_start() {
		return this.morning_start;
	}

	public void setMorning_start(String morning_start) {
		this.morning_start = morning_start;
	}

	public String getMorning_end() {
		return this.morning_end;
	}

	public void setMorning_end(String morning_end) {
		this.morning_end = morning_end;
	}

	public String getDay_start() {
		return this.day_start;
	}

	public void setDay_start(String day_start) {
		this.day_start = day_start;
	}

	public String getDay_end() {
		return this.day_end;
	}

	public void setDay_end(String day_end) {
		this.day_end = day_end;
	}

	public String getEvening_start() {
		return this.evening_start;
	}

	public void setEvening_start(String evening_start) {
		this.evening_start = evening_start;
	}

	public String getEvening_end() {
		return this.evening_end;
	}

	public void setEvening_end(String evening_end) {
		this.evening_end = evening_end;
	}

	public String getNight_start() {
		return this.night_start;
	}

	public void setNight_start(String night_start) {
		this.night_start = night_start;
	}

	public String getNight_end() {
		return this.night_end;
	}

	public void setNight_end(String night_end) {
		this.night_end = night_end;
	}

}