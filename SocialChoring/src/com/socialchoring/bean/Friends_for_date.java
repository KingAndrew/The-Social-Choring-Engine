package com.socialchoring.bean;

public class Friends_for_date {
	private int id;
	private String begin_date;// the date at which this friendship started.
	private int player_one;// foreign key to PLAYER for the owner
	private int player_two;// foreign key to PLAYER for the friends

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBegin_date() {
		return this.begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public int getPlayer_one() {
		return this.player_one;
	}

	public void setPlayer_one(int player_one) {
		this.player_one = player_one;
	}

	public int getPlayer_two() {
		return this.player_two;
	}

	public void setPlayer_two(int player_two) {
		this.player_two = player_two;
	}

}