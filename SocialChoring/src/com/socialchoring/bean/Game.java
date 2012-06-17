package com.socialchoring.bean;

public class Game {
	private int id;
	private String company;
	private String name_of_game;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName_of_game() {
		return this.name_of_game;
	}

	public void setName_of_game(String name_of_game) {
		this.name_of_game = name_of_game;
	}

}