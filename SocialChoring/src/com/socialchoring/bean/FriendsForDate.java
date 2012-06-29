package com.socialchoring.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FriendsForDate {
	private long id;
	private String begin_date;// the date at which this friendship started.
	private long player_one;// foreign key to PLAYER for the owner
	private long player_two;// foreign key to PLAYER for the friends

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBegin_date() {
		return this.begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public long getPlayer_one() {
		return this.player_one;
	}

	public void setPlayer_one(long player_one) {
		this.player_one = player_one;
	}

	public long getPlayer_two() {
		return this.player_two;
	}

	public void setPlayer_two(int player_two) {
		this.player_two = player_two;
	}

}