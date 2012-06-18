package com.socialchoring.bean;

public class PlayerGames {
	private int id;
	private int game_id;
	private int player_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGame_id() {
		return this.game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getPlayer_id() {
		return this.player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

}