package com.socialchoring.service;

import java.util.Date;
import java.util.List;

import com.socialchoring.bean.Player;

public interface SocialChoringService {
	/**
	 * Login to the system
	 * 
	 * @param parentEmail
	 * @param userName
	 * @param password
	 * @return I'm not sure what to return. Probably a session id but you can
	 *         decide.
	 */
	public String login(String parentEmail, String userName, String password);

	/**
	 * Create an Account
	 * 
	 * @param parent_first_name
	 *            Parent first name max length 40
	 * @param parent_last_name
	 *            Parent last name max length 40
	 * @param parent_email
	 *            Parent email must be unique max length 50
	 * @param player_first_name
	 *            Player first name max length 40
	 * @return Some value greater than zero representing the account id. If 0 is
	 *         returned the call was not successful;
	 * @throws Throwable
	 */
	public long createAccount(String parent_first_name, String parent_last_name, String parent_email, String player_first_name);

	/**
	 * Not Implemented I don't remember the reason for this. It could be a
	 * sanity check that the chores Scheduled can actually be done in the time
	 * allotted.
	 * 
	 * @return
	 */
	public boolean validateAccount(long account_id);

	/**
	 * Not Implemented
	 * 
	 * @return
	 */
	public boolean disableAccount(long account_id);

	/**
	 * Adds a new player to the given Account
	 * 
	 * @param account_id
	 *            Id of the account that is adding the player.
	 * @param player_first_name
	 *            Max length of 40
	 * @return true for success false for failure.
	 */
	public boolean addNewPlayerToAccount(long account_id, String player_first_name);

	/**
	 * Returns all the players for the given account_id
	 * 
	 * @param account_id
	 * @return
	 */
	public List<Player> getPlayersForAccount(long account_id);

	/**
	 * Create a chore plan for the given player
	 * 
	 * @param player_id
	 * @param chore_plan_to_copy_id
	 *            The id of the chore plan to copy. If null the chore plan will
	 *            be copied from Default chore plan
	 * @return
	 */
	public long createChorePlanForPlayer(long player_id, long chore_plan_to_copy_id);

	/**
	 * Creates a connection between two players
	 * 
	 * @param date
	 * @param player_one_id
	 * @param player_two_id
	 * @return
	 */
	public boolean createFriends(Date date, long player_one_id, long player_two_id);

	// Not Implemented
	/**
	 * Returns all the friends (aka their court) for a given player
	 * 
	 * @return
	 */
	public boolean getFriendsForPlayer();

	/**
	 * Records the start time for the given chore
	 * 
	 * @param chore_plan_id
	 *            This points to a specific chore
	 * @param time_started
	 * @param date_observed
	 * @return
	 */
	public long startChore(long chore_plan_id, long time_started, Date date_observed);

	/**
	 * Records the stop time for the given chore
	 * 
	 * @param chore_observed_id
	 *            This points to a specific chore
	 * @param time_stoped
	 * @param did_complete
	 * @return
	 */
	public boolean stopChore(long chore_observed_id, long time_stoped, boolean did_complete);

	// /**
	// * Get player's all chore plans by player id
	// *
	// * @param playerid
	// * @return
	// */
	// public List<PlayerChorePlan> getChorePlanByPlayerId(long playerId);

	/**
	 * This should be call by a cron job. This is the big daddy that gets run
	 * once a day and calculates all the winners.
	 */
	public boolean calculateWinners(Date date_observed);
	

	/**
	 * Delete Account with all its players and related chore informations(friendship, chore plan, chore observed and etc.)
	 * 
	 * @param playerid
	 * @return
	 */
	public boolean deleteAccount(long acountId);

}
