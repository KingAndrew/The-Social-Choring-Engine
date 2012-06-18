package com.socialchoring.bean;

public class PlayerChoreObserved {
	private int id;
	private int chore_plan_id;
	private String date_observed;
	private String time_ended;
	private String time_started;// when the chore was started.
	private String max_time;
	private String min_time;
	private String ideal_time;
	private boolean was_in_time;// calculated by observed_time <= max_time
	private boolean did_complete;// needs to be true whenever ended_time is
									// !NULL and ended_time > min_time
	private int earnings;// what the player earned for this chore. calculated by
							// if(did_complete){ +1 if(min_time >= observed_time
							// <=max_time) {+1 if(min_time >= observed_time <=
							// ideal_time) +1}}}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChore_plan_id() {
		return this.chore_plan_id;
	}

	public void setChore_plan_id(int chore_plan_id) {
		this.chore_plan_id = chore_plan_id;
	}

	public String getDate_observed() {
		return this.date_observed;
	}

	public void setDate_observed(String date_observed) {
		this.date_observed = date_observed;
	}

	public String getTime_ended() {
		return this.time_ended;
	}

	public void setTime_ended(String time_ended) {
		this.time_ended = time_ended;
	}

	public String getTime_started() {
		return this.time_started;
	}

	public void setTime_started(String time_started) {
		this.time_started = time_started;
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

	public String getIdeal_time() {
		return this.ideal_time;
	}

	public void setIdeal_time(String ideal_time) {
		this.ideal_time = ideal_time;
	}

	public boolean was_in_time() {
		return this.was_in_time;
	}

	public void was_in_time(boolean was_in_time) {
		this.was_in_time = was_in_time;
	}

	public boolean did_complete() {
		return this.did_complete;
	}

	public void did_complete(boolean did_complete) {
		this.did_complete = did_complete;
	}

	public int getEarnings() {
		return this.earnings;
	}

	public void setEarnings(int earnings) {
		this.earnings = earnings;
	}

}