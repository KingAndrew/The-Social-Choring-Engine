package com.socialchoring.engine.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerGames {
	private long id;
	private long game_id;
	private long player_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGame_id() {
		return this.game_id;
	}

	public void setGame_id(long game_id) {
		this.game_id = game_id;
	}

	public long getPlayer_id() {
		return this.player_id;
	}

	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}

}