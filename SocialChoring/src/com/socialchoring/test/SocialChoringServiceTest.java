package com.socialchoring.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.socialchoring.bean.Player;
import com.socialchoring.bean.Player_chore_plan;
import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;

public class SocialChoringServiceTest {

	@Test
	public void testCalculateWinners4ScenarioA() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		List<Player> players = new ArrayList<Player>();

		// 1. Create several accounts by calling createAccount (say 3 accounts
		// which means 3 parents, right?)
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i,
					"parent_last_name " + i, "parent_email@xxx.us" + i,
					"player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 2 more players to account by calling addNewPlayerToAccount
		// (then we have 5 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name5");
		Assert.assertTrue(returnValue);

		// 3. make the 5 players to be friends by calling createFriends
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);

		// -- I saw we already have several chores in master_chore table, so I
		// will just use them and not create new chores.
		// 4. add 7 chore plans for the each player by calling
		// createChorePlanForPlayer (could be same chores?)
		for (Player player : players) {
			returnValue = service.createChorePlanForPlayer(player.getId(), 1);
			Assert.assertTrue(returnValue);
			returnValue = service.createChorePlanForPlayer(player.getId(), 2);
			Assert.assertTrue(returnValue);
			returnValue = service.createChorePlanForPlayer(player.getId(), 3);
			Assert.assertTrue(returnValue);
			returnValue = service.createChorePlanForPlayer(player.getId(), 4);
			Assert.assertTrue(returnValue);
			returnValue = service.createChorePlanForPlayer(player.getId(), 5);
			Assert.assertTrue(returnValue);
			returnValue = service.createChorePlanForPlayer(player.getId(), 6);
			Assert.assertTrue(returnValue);
			returnValue = service.createChorePlanForPlayer(player.getId(), 7);
			Assert.assertTrue(returnValue);
		}

		// 5. players start to play their chores by calling startChore
		for (Player player : players) {
			List<Player_chore_plan> chorePlans = service
					.getChorePlanByPlayerId(player.getId());
			for (Player_chore_plan plan : chorePlans) {
				returnValue = service
						.startChore(plan.getId(), new Date(), null);
				Assert.assertTrue(returnValue);
			}
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the folllowing score UCanCash
		// matrix.
		//
		// Player1 = 1, 2, 3, 1, 0, 2, 3
		// Player2 = 2, 3, 1, 3, 2, 1, 2
		// player3 = 2, 1, 3, 2, 1, 1, 1
		// player4 = 1, 1, 1, 1, 1, 1, 1
		// player5 = 1, 3, 2, 1, 0, 2, 1
		for (Player player : players) {
			// List<Player_chore_observed> choreObserves = service
			// .getChoreObservedByChorePlanId(player.getId());
			// for (Player_chore_plan plan : chorePlans) {
			// returnValue = service.stopChore(chore_observed_id, time_stoped,
			// did_complete);
			// Assert.assertTrue(returnValue);
			// }
		}

		// 7. call calculateWinners to calculate the winners
		//
		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the earnings match the below table
		//
		// Player2 = 82.5
		// Player3 = 52.8
		// Player1 = 36.3
		// Player5 = 22
		// Player4 = 7.7

		// 9. delete all the test data
		deleteAll();
	}

	@Test
	public void testCalculateWinners4ScenarioB() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		List<Player> players = new ArrayList<Player>();

		// 1. Create 3 accounts by calling createAccount
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i,
					"parent_last_name " + i, "parent_email@xxx.us" + i,
					"player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 2 more players to account by calling addNewPlayerToAccount
		// (then we have 5 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name5");
		Assert.assertTrue(returnValue);

		// 3. make the 5 players to be friends by calling createFriends
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);

		// 4. add different chores to each player as the following matrix
		// Player1 = 3, 3, 3,
		// player2 = 3, 3, 3, 3
		// player3 = 3, 3
		// player4 = 3, 3, 3, 3, 3, 3, 3
		// player5 = 3, 3, 3, 3, 3
		for (Player player : players) {
			if (player.getFirst_name().contains("1")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("2")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("3")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("4")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						6);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						7);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("5")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
			}
		}

		// 5. players start to play their chores by calling startChore
		for (Player player : players) {
			List<Player_chore_plan> chorePlans = service
					.getChorePlanByPlayerId(player.getId());
			for (Player_chore_plan plan : chorePlans) {
				returnValue = service
						.startChore(plan.getId(), new Date(), null);
				Assert.assertTrue(returnValue);
			}
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the above score UCanCash
		// matrix.(all 3)
		for (Player player : players) {
			// List<Player_chore_observed> choreObserves = service
			// .getChoreObservedByChorePlanId(player.getId());
			// for (Player_chore_plan plan : chorePlans) {
			// returnValue = service.stopChore(chore_observed_id, time_stoped,
			// did_complete);
			// Assert.assertTrue(returnValue);
			// }
		}

		// 7. call calculateWinners to calculate the winners
		//
		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the earnings match the below table
		// Player1 = 19.8
		// player2 = 39.6
		// player3 = 6.6
		// player4 = 115
		// player5 = 66

		// 9. delete all the test data
		deleteAll();
	}

	public void testCalculateWinners4ScenarioC() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		List<Player> players = new ArrayList<Player>();

		// 1. Create 3 accounts by calling createAccount
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i,
					"parent_last_name " + i, "parent_email@xxx.us" + i,
					"player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 2 more players to account by calling addNewPlayerToAccount
		// (then we have 5 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name5");
		Assert.assertTrue(returnValue);

		// 3. make the 5 players to be friends by calling createFriends
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);

		// 4. add different chores to each player as the following matrix
		// Player1 = 1, 0, 3
		// player2 = 3, 2, 2, 3
		// player3 = 3, 3
		// player4 = 2, 1, 0, 0, 2, 2, 2
		// player5 = 2, 3, 1, 2, 2
		for (Player player : players) {
			if (player.getFirst_name().contains("1")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("2")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("3")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("4")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						6);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						7);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("5")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
			}
		}

		// 5. players start to play their chores by calling startChore
		for (Player player : players) {
			List<Player_chore_plan> chorePlans = service
					.getChorePlanByPlayerId(player.getId());
			for (Player_chore_plan plan : chorePlans) {
				returnValue = service
						.startChore(plan.getId(), new Date(), null);
				Assert.assertTrue(returnValue);
			}
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the above score UCanCash
		// matrix. (tie for first.)
		for (Player player : players) {
			// List<Player_chore_observed> choreObserves = service
			// .getChoreObservedByChorePlanId(player.getId());
			// for (Player_chore_plan plan : chorePlans) {
			// returnValue = service.stopChore(chore_observed_id, time_stoped,
			// did_complete);
			// Assert.assertTrue(returnValue);
			// }
		}

		// 7. call calculateWinners to calculate the winners

		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the earnings match the below table
		// Player2 + 55
		// Player5 + 55
		// Player4 + 29.7
		// Player3 + 13.2
		// Player1 + 4.4

		// 9. delete all the test data
		deleteAll();

	}

	public void testCalculateWinners4ScenarioD() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		List<Player> players = new ArrayList<Player>();

		// 1. Create 3 accounts by calling createAccount
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i,
					"parent_last_name " + i, "parent_email@xxx.us" + i,
					"player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 2 more players to account by calling addNewPlayerToAccount
		// (then we have 7 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name5");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name6");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name7");
		Assert.assertTrue(returnValue);

		// 3. some of them are friends, friends matrix as below
		// Player1 Castle
		// Player1 = 1, 0, 4, = 5
		// Player2 = 3, 2, 2, 3 = 10 ¡û winner
		//
		// Player2 Castle
		// Player1 = 1, 0, 4, = 5
		// Player2 = 3, 2, 2, 3 = 10
		// Player3 = 3, 3, 3, 3 = 12 ¡û winner
		//
		// Player3 Castle
		// Player3 = 3, 3, 3, 3 = 12
		// Player2 = 3, 2, 2, 3 = 10
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 ¡û winner
		// Player5 = 2, 3, 1, 2, 2, = 10
		//
		//
		// Player4 Castle
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 ¡û winner & King
		// Player3 = 3, 3, 3, 3 = 12
		// Player5 = 2, 3, 1, 2, 2, = 10
		// Player6 = 3, 3, 0, 3 = 09
		// Player7 = 2, 3, 1, 2, 2, = 10
		//
		//
		// Player5 Castle
		// Player3 = 3, 3, 3, 3 = 12
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 ¡û winner
		// Player5 = 2, 3, 1, 2, 2, = 10

		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(5).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(7).getId());
		Assert.assertTrue(returnValue);

		// 4. add different chores to each player as the above matrix
		for (Player player : players) {
			if (player.getFirst_name().contains("1")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("2")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("3")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("4")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						6);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						7);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("5")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("6")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("7")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
			}
		}

		// 5. players start to play their chores by calling startChore
		for (Player player : players) {
			List<Player_chore_plan> chorePlans = service
					.getChorePlanByPlayerId(player.getId());
			for (Player_chore_plan plan : chorePlans) {
				returnValue = service
						.startChore(plan.getId(), new Date(), null);
				Assert.assertTrue(returnValue);
			}
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the above score UCanCash
		// matrix.
		for (Player player : players) {
			// List<Player_chore_observed> choreObserves = service
			// .getChoreObservedByChorePlanId(player.getId());
			// for (Player_chore_plan plan : chorePlans) {
			// returnValue = service.stopChore(chore_observed_id, time_stoped,
			// did_complete);
			// Assert.assertTrue(returnValue);
			// }
		}

		// 7. call calculateWinners to calculate the winners

		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the king is player4

		// 9. delete all the test data
		deleteAll();

	}

	public void testCalculateWinners4ScenarioE() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		List<Player> players = new ArrayList<Player>();

		// 1. Create 3 accounts by calling createAccount
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i,
					"parent_last_name " + i, "parent_email@xxx.us" + i,
					"player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 2 more players to account by calling addNewPlayerToAccount
		// (then we have 7 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name5");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name6");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2],
				"player_first_name7");
		Assert.assertTrue(returnValue);

		// 3. some of them are friends, friends matrix as below
		// Player1 Castle
		// Player1 = 1, 0, 3, = 4
		// Player2 = 3, 3, 3, 3, 3 = 15 ¡û winner
		//
		// Player2 Castle
		// Player1 = 1, 0, 3, = 4
		// Player2 = 3, 3, 3, 3, 3 = 15 ¡û winner & NOT King. Two members short.
		// Must have 5 in court
		// Player3 = 3, 3, 3, 3 = 12
		//
		// Player3 Castle
		// Player3 = 3, 3, 3, 3 = 12
		// Player2 = 3, 2, 2, 3 = 15 ¡û winner
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13
		// Player5 = 2, 3, 1, 2, 2, = 10
		//
		// Player4 Castle
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 ¡û winner & King
		// Player3 = 3, 3, 3, 3 = 12
		// Player5 = 2, 3, 1, 2, 2, = 10
		// Player6 = 0, 1, 2, 3, 0 = 6
		// Player7 = 0, 1, 2, 3, 3 = 9
		//
		//
		// Player5 Castle
		// Player3 = 3, 3, 3, 3 = 12
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 ¡û winner
		// Player5 = 2, 3, 1, 2, 2, = 10
		//
		// Player6 Castle
		// Player6 = 0, 1, 2, 3, 0 = 6
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 ¡û winner
		//
		// Player7 Castle
		// Player7 = 0, 1, 2, 3, 3 = 9
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 ¡û winner

		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(),
				players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(),
				players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(5).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(),
				players.get(7).getId());
		Assert.assertTrue(returnValue);

		
		// 4. add different chores to each player as the above matrix
		for (Player player : players) {
			if (player.getFirst_name().contains("1")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("2")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("3")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("4")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						6);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						7);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("5")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("6")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
			} else if (player.getFirst_name().contains("7")) {
				returnValue = service.createChorePlanForPlayer(player.getId(),
						1);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						2);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						3);
				Assert.assertTrue(returnValue);
				returnValue = service.createChorePlanForPlayer(player.getId(),
						4);
				Assert.assertTrue(returnValue);

				returnValue = service.createChorePlanForPlayer(player.getId(),
						5);
				Assert.assertTrue(returnValue);
			}
		}

		// 5. players start to play their chores by calling startChore
		for (Player player : players) {
			List<Player_chore_plan> chorePlans = service
					.getChorePlanByPlayerId(player.getId());
			for (Player_chore_plan plan : chorePlans) {
				returnValue = service
						.startChore(plan.getId(), new Date(), null);
				Assert.assertTrue(returnValue);
			}
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the above score UCanCash
		// matrix.
		for (Player player : players) {
			// List<Player_chore_observed> choreObserves = service
			// .getChoreObservedByChorePlanId(player.getId());
			// for (Player_chore_plan plan : chorePlans) {
			// returnValue = service.stopChore(chore_observed_id, time_stoped,
			// did_complete);
			// Assert.assertTrue(returnValue);
			// }
		}

		// 7. call calculateWinners to calculate the winners

		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the king is player4

		// 9. delete all the test data
		deleteAll();


	}
	
	private void deleteAll() {
		// TODO Auto-generated method stub

	}
}
