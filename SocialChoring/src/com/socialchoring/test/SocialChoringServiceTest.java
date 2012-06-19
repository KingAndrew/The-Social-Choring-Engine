package com.socialchoring.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.socialchoring.bean.Player;
import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;

public class SocialChoringServiceTest {

	@Test
	public void testCalculateWinners4ScenarioA() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		long[][] chorePlanId = new long[5][7];
		long[][] choreObservedId = new long[5][7];
		List<Player> players = new ArrayList<Player>();

		// 1. Create several accounts by calling createAccount (say 3 accounts
		// which means 3 parents, right?)
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i, "parent_last_name " + i, "parent_email@xxx.us" + i, "player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 2 more players to account by calling addNewPlayerToAccount
		// (then we have 5 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name5");
		Assert.assertTrue(returnValue);

		// 3. make the 5 players to be friends by calling createFriends
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);

		// -- I saw we already have several chores in master_chore table, so I
		// will just use them and not create new chores.
		// 4. add 7 chore plans for the each player by calling
		// createChorePlanForPlayer (could be same chores?)
		int i = 0;
		for (Player player : players) {
			chorePlanId[i][0] = service.createChorePlanForPlayer(player.getId(), 1);
			Assert.assertTrue(chorePlanId[i][0] > 0);
			chorePlanId[i][1] = service.createChorePlanForPlayer(player.getId(), 2);
			Assert.assertTrue(chorePlanId[i][1] > 0);
			chorePlanId[i][2] = service.createChorePlanForPlayer(player.getId(), 3);
			Assert.assertTrue(chorePlanId[i][2] > 0);
			chorePlanId[i][3] = service.createChorePlanForPlayer(player.getId(), 4);
			Assert.assertTrue(chorePlanId[i][3] > 0);
			chorePlanId[i][4] = service.createChorePlanForPlayer(player.getId(), 5);
			Assert.assertTrue(chorePlanId[i][4] > 0);
			chorePlanId[i][5] = service.createChorePlanForPlayer(player.getId(), 6);
			Assert.assertTrue(chorePlanId[i][5] > 0);
			chorePlanId[i][6] = service.createChorePlanForPlayer(player.getId(), 7);
			Assert.assertTrue(chorePlanId[i][6] > 0);
			i++;
		}

		// 5. players start to play their chores by calling startChore
		i = 0;
		for (Player player : players) {
			for (int j = 0; j < 7; j++) {
				if (player.getFirst_name().contains("1") || player.getFirst_name().contains("5")) {
					if (j == 4) {
						// see matrix below
						continue;
					}
				}
				choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
				Assert.assertTrue(choreObservedId[i][j] > 0);
			}
			i++;
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the folllowing score UCanCash
		// matrix.

		// Player1 = 1, 2, 3, 1, 0, 2, 3
		returnValue = service.stopChore(choreObservedId[0][0], 1, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[0][1], 8, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[0][2], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[0][3], 6, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[0][5], 20, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[0][6], 8, true);
		Assert.assertTrue(returnValue);

		// Player2 = 2, 3, 1, 3, 2, 1, 2
		returnValue = service.stopChore(choreObservedId[1][0], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][2], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][3], 8, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][4], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][5], 20, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][6], 12, true);
		Assert.assertTrue(returnValue);

		// player3 = 2, 1, 3, 2, 1, 1, 1
		returnValue = service.stopChore(choreObservedId[1][0], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][1], 4, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][2], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][3], 8, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][4], 5, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][5], 20, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][6], 12, false);
		Assert.assertTrue(returnValue);

		// player4 = 1, 1, 1, 1, 1, 1, 1
		returnValue = service.stopChore(choreObservedId[1][0], 5, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][1], 4, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][2], 2, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][3], 8, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][4], 5, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][5], 20, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][6], 12, false);
		Assert.assertTrue(returnValue);

		// player5 = 1, 3, 2, 1, 0, 2, 1
		returnValue = service.stopChore(choreObservedId[1][0], 5, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][2], 10, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][3], 8, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][5], 14, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][6], 12, false);
		Assert.assertTrue(returnValue);

		// 7. call calculateWinners to calculate the winners
		returnValue = service.calculateWinners(new Date());
		Assert.assertTrue(returnValue);

		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the earnings match the below table
		players.clear();
		for (i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}
		// Player1 = 36.3
		Assert.assertEquals(players.get(0).getEarnings(), "36.3");
		Assert.assertEquals(players.get(0).getChampion_count(), 0);
		// Player2 = 82.5 -- winner
		Assert.assertEquals(players.get(1).getEarnings(), "82.5");
		Assert.assertEquals(players.get(1).getChampion_count(), 1);
		// Player3 = 52.8
		Assert.assertEquals(players.get(2).getEarnings(), "52.8");
		Assert.assertEquals(players.get(2).getChampion_count(), 0);
		// Player4 = 7.7
		Assert.assertEquals(players.get(3).getEarnings(), "7.7");
		Assert.assertEquals(players.get(3).getChampion_count(), 0);
		// Player5 = 22
		Assert.assertEquals(players.get(4).getEarnings(), "22");
		Assert.assertEquals(players.get(4).getChampion_count(), 0);

		// 9. delete all the test data
		deleteAll(service, accountid);
	}

	@Test
	public void testCalculateWinners4ScenarioB() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		long[][] chorePlanId = new long[5][7];
		long[][] choreObservedId = new long[5][7];
		List<Player> players = new ArrayList<Player>();

		// 1. Create 3 accounts by calling createAccount
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i, "parent_last_name " + i, "parent_email@xxx.us" + i, "player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 2 more players to account by calling addNewPlayerToAccount
		// (then we have 5 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name5");
		Assert.assertTrue(returnValue);

		// 3. make the 5 players to be friends by calling createFriends
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);

		// 4. add chores to each player as the following matrix
		// Player1 = 3, 3, 3,
		// player2 = 3, 3, 3, 3
		// player3 = 3, 3
		// player4 = 3, 3, 3, 3, 3, 3, 3
		// player5 = 3, 3, 3, 3, 3
		for (Player player : players) {
			if (player.getFirst_name().contains("1")) {
				chorePlanId[0][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[0][0] > 0);
				chorePlanId[0][1] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[0][1] > 0);
				chorePlanId[0][2] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[0][2] > 0);
			} else if (player.getFirst_name().contains("2")) {
				chorePlanId[1][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[1][0] > 0);
				chorePlanId[1][1] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[1][1] > 0);
				chorePlanId[1][2] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[1][2] > 0);
				chorePlanId[1][3] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[1][3] > 0);
			} else if (player.getFirst_name().contains("3")) {
				chorePlanId[2][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[2][0] > 0);
				chorePlanId[2][1] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[2][1] > 0);
			} else if (player.getFirst_name().contains("4")) {
				chorePlanId[3][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][0] > 0);
				chorePlanId[3][1] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][1] > 0);
				chorePlanId[3][2] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][2] > 0);
				chorePlanId[3][3] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][3] > 0);
				chorePlanId[3][4] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][4] > 0);
				chorePlanId[3][5] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][5] > 0);
				chorePlanId[3][6] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][6] > 0);
			} else if (player.getFirst_name().contains("5")) {
				chorePlanId[4][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[4][0] > 0);
				chorePlanId[4][1] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[4][1] > 0);
				chorePlanId[4][2] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[4][2] > 0);
				chorePlanId[4][3] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[4][3] > 0);
				chorePlanId[4][4] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[4][4] > 0);
			}
		}

		// 5. players start to play their chores by calling startChore
		for (int i = 0; i < 5; i++) {
			if (i == 0) {
				for (int j = 0; j < 3; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}

			} else if (i == 1) {
				for (int j = 0; j < 4; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 2) {
				for (int j = 0; j < 2; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 3) {
				for (int j = 0; j < 7; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 4) {
				for (int j = 0; j < 5; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			}
			i++;
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the above score UCanCash
		// matrix.(all 3)
		for (int i = 0; i < 5; i++) {
			if (i == 0) {
				for (int j = 0; j < 3; j++) {
					returnValue = service.stopChore(choreObservedId[i][j], 2, true);
					Assert.assertTrue(returnValue);
				}

			} else if (i == 1) {
				for (int j = 0; j < 4; j++) {
					returnValue = service.stopChore(choreObservedId[i][j], 2, true);
					Assert.assertTrue(returnValue);
				}
			} else if (i == 2) {
				for (int j = 0; j < 2; j++) {
					returnValue = service.stopChore(choreObservedId[i][j], 2, true);
					Assert.assertTrue(returnValue);
				}
			} else if (i == 3) {
				for (int j = 0; j < 7; j++) {
					returnValue = service.stopChore(choreObservedId[i][j], 2, true);
					Assert.assertTrue(returnValue);
				}
			} else if (i == 4) {
				for (int j = 0; j < 5; j++) {
					returnValue = service.stopChore(choreObservedId[i][j], 2, true);
					Assert.assertTrue(returnValue);
				}
			}
			i++;
		}

		// 7. call calculateWinners to calculate the winners
		returnValue = service.calculateWinners(new Date());
		Assert.assertTrue(returnValue);

		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the earnings match the below table
		players.clear();
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}
		// Player1 = 19.8
		Assert.assertEquals(players.get(0).getEarnings(), "19.8");
		Assert.assertEquals(players.get(0).getChampion_count(), 0);
		// Player2 = 39.6
		Assert.assertEquals(players.get(1).getEarnings(), "39.6");
		Assert.assertEquals(players.get(1).getChampion_count(), 0);
		// Player3 = 6.6
		Assert.assertEquals(players.get(2).getEarnings(), "6.6");
		Assert.assertEquals(players.get(2).getChampion_count(), 0);
		// Player4 = 115 -- winner
		Assert.assertEquals(players.get(3).getEarnings(), "115");
		Assert.assertEquals(players.get(3).getChampion_count(), 1);
		// Player5 = 66
		Assert.assertEquals(players.get(4).getEarnings(), "66");
		Assert.assertEquals(players.get(4).getChampion_count(), 0);

		// 9. delete all the test data
		deleteAll(service, accountid);
	}

	@Test
	public void testCalculateWinners4ScenarioC() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		long[][] chorePlanId = new long[5][7];
		long[][] choreObservedId = new long[5][7];
		List<Player> players = new ArrayList<Player>();

		// 1. Create 3 accounts by calling createAccount
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i, "parent_last_name " + i, "parent_email@xxx.us" + i, "player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 2 more players to account by calling addNewPlayerToAccount
		// (then we have 5 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name5");
		Assert.assertTrue(returnValue);

		// 3. make the 5 players to be friends by calling createFriends
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);

		// 4. add different chores to each player as the following matrix
		// Player1 = 1, 0, 3
		// player2 = 3, 2, 2, 3
		// player3 = 3, 3
		// player4 = 2, 1, 0, 0, 2, 2, 2
		// player5 = 2, 3, 1, 2, 2
		for (Player player : players) {
			if (player.getFirst_name().contains("1")) {
				chorePlanId[0][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[0][0] > 0);
				chorePlanId[0][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[0][1] > 0);
				chorePlanId[0][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[0][2] > 0);
			} else if (player.getFirst_name().contains("2")) {
				chorePlanId[1][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[1][0] > 0);
				chorePlanId[1][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[1][1] > 0);
				chorePlanId[1][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[1][2] > 0);
				chorePlanId[1][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[1][3] > 0);
			} else if (player.getFirst_name().contains("3")) {
				chorePlanId[2][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[2][0] > 0);
				chorePlanId[2][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[2][1] > 0);
			} else if (player.getFirst_name().contains("4")) {
				chorePlanId[3][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][0] > 0);
				chorePlanId[3][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[3][1] > 0);
				chorePlanId[3][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[3][2] > 0);
				chorePlanId[3][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[3][3] > 0);
				chorePlanId[3][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[3][4] > 0);
				chorePlanId[3][5] = service.createChorePlanForPlayer(player.getId(), 6);
				Assert.assertTrue(chorePlanId[3][5] > 0);
				chorePlanId[3][6] = service.createChorePlanForPlayer(player.getId(), 7);
				Assert.assertTrue(chorePlanId[3][6] > 0);
			} else if (player.getFirst_name().contains("5")) {
				chorePlanId[4][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[4][0] > 0);
				chorePlanId[4][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[4][1] > 0);
				chorePlanId[4][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[4][2] > 0);
				chorePlanId[4][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[4][3] > 0);
				chorePlanId[4][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[4][4] > 0);
			}
		}

		// 5. players start to play their chores by calling startChore
		for (int i = 0; i < 5; i++) {
			if (i == 0) {
				for (int j = 0; j < 3; j++) {
					if (j == 1) {
						continue;
					}
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}

			} else if (i == 1) {
				for (int j = 0; j < 4; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 2) {
				for (int j = 0; j < 2; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 3) {
				for (int j = 0; j < 7; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 4) {
				for (int j = 0; j < 5; j++) {
					if (j == 2 || j == 3) {
						continue;
					}
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			}
			i++;
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the above score UCanCash
		// matrix. (tie for first.)

		// Player1 = 1, 0, 3
		returnValue = service.stopChore(choreObservedId[0][0], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[0][2], 3, true);
		Assert.assertTrue(returnValue);
		// player2 = 3, 2, 2, 3
		returnValue = service.stopChore(choreObservedId[1][0], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][1], 6, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][2], 10, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][3], 8, true);
		Assert.assertTrue(returnValue);
		// player3 = 3, 3
		returnValue = service.stopChore(choreObservedId[2][0], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[2][1], 4, true);
		Assert.assertTrue(returnValue);
		// player4 = 2, 1, 0, 0, 2, 2, 2
		returnValue = service.stopChore(choreObservedId[3][0], 10, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][1], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][4], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][5], 13, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][6], 13, true);
		Assert.assertTrue(returnValue);
		// player5 = 2, 3, 1, 2, 2
		returnValue = service.stopChore(choreObservedId[4][0], 10, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][1], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][2], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][3], 13, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][4], 5, true);
		Assert.assertTrue(returnValue);

		// 7. call calculateWinners to calculate the winners
		returnValue = service.calculateWinners(new Date());
		Assert.assertTrue(returnValue);

		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the earnings match the below table
		players.clear();
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}
		// Player1 = 4.4
		Assert.assertEquals(players.get(0).getEarnings(), "4.4");
		Assert.assertEquals(players.get(0).getChampion_count(), 0);
		// Player2 = 55 -- winner
		Assert.assertEquals(players.get(1).getEarnings(), "55");
		Assert.assertEquals(players.get(1).getChampion_count(), 1);
		// Player3 = 13.2
		Assert.assertEquals(players.get(2).getEarnings(), "13.2");
		Assert.assertEquals(players.get(2).getChampion_count(), 0);
		// Player4 = 29.7
		Assert.assertEquals(players.get(3).getEarnings(), "29.7");
		Assert.assertEquals(players.get(3).getChampion_count(), 0);
		// Player5 = 55 -- winner
		Assert.assertEquals(players.get(4).getEarnings(), "55");
		Assert.assertEquals(players.get(4).getChampion_count(), 1);

		// 9. delete all the test data
		deleteAll(service, accountid);

	}
	
	@Test
	public void testCalculateWinners4ScenarioD() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		long[][] chorePlanId = new long[7][7];
		long[][] choreObservedId = new long[7][7];
		List<Player> players = new ArrayList<Player>();

		// 1. Create 3 accounts by calling createAccount
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i, "parent_last_name " + i, "parent_email@xxx.us" + i, "player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 4 more players to account by calling addNewPlayerToAccount
		// (then we have 7 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name5");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name6");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name7");
		Assert.assertTrue(returnValue);

		// 3. some of them are friends, friends matrix as below
		// Player1 Castle
		// Player1 = 1, 0, 3, = 4
		// Player2 = 3, 2, 2, 3 = 10 -- winner
		//
		// Player2 Castle
		// Player1 = 1, 0, 3, = 4
		// Player2 = 3, 2, 2, 3 = 10
		// Player3 = 3, 3, 3, 3 = 12 -- winner
		//
		// Player3 Castle
		// Player3 = 3, 3, 3, 3 = 12
		// Player2 = 3, 2, 2, 3 = 10
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 -- winner
		// Player5 = 2, 3, 1, 2, 2, = 10
		//
		//
		// Player4 Castle
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 -- winner & King
		// Player3 = 3, 3, 3, 3 = 12
		// Player5 = 2, 3, 1, 2, 2, = 10
		// Player6 = 3, 3, 0, 3 = 09
		// Player7 = 2, 3, 1, 2, 2, = 10
		//
		//
		// Player5 Castle
		// Player3 = 3, 3, 3, 3 = 12
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 -- winner
		// Player5 = 2, 3, 1, 2, 2, = 10
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}
		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(5).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(7).getId());
		Assert.assertTrue(returnValue);

		// 4. add different chores to each player as the above matrix
		for (Player player : players) {
			if (player.getFirst_name().contains("1")) {
				chorePlanId[0][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[0][0] > 0);
				chorePlanId[0][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[0][1] > 0);
				chorePlanId[0][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[0][2] > 0);
			} else if (player.getFirst_name().contains("2")) {
				chorePlanId[1][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[1][0] > 0);
				chorePlanId[1][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[1][1] > 0);
				chorePlanId[1][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[1][2] > 0);
				chorePlanId[1][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[1][3] > 0);
			} else if (player.getFirst_name().contains("3")) {
				chorePlanId[2][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[2][0] > 0);
				chorePlanId[2][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[2][1] > 0);
				chorePlanId[2][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[2][2] > 0);
				chorePlanId[2][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[2][3] > 0);
			} else if (player.getFirst_name().contains("4")) {
				chorePlanId[3][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][0] > 0);
				chorePlanId[3][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[3][1] > 0);
				chorePlanId[3][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[3][2] > 0);
				chorePlanId[3][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[3][3] > 0);
				chorePlanId[3][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[3][4] > 0);
				chorePlanId[3][5] = service.createChorePlanForPlayer(player.getId(), 6);
				Assert.assertTrue(chorePlanId[3][5] > 0);
				chorePlanId[3][6] = service.createChorePlanForPlayer(player.getId(), 7);
				Assert.assertTrue(chorePlanId[3][6] > 0);
			} else if (player.getFirst_name().contains("5")) {
				chorePlanId[4][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[4][0] > 0);
				chorePlanId[4][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[4][1] > 0);
				chorePlanId[4][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[4][2] > 0);
				chorePlanId[4][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[4][3] > 0);
				chorePlanId[4][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[4][4] > 0);
			} else if (player.getFirst_name().contains("6")) {
				chorePlanId[5][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[5][0] > 0);
				chorePlanId[5][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[5][1] > 0);
				chorePlanId[5][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[5][2] > 0);
				chorePlanId[5][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[5][3] > 0);
			} else if (player.getFirst_name().contains("7")) {
				chorePlanId[6][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[6][0] > 0);
				chorePlanId[6][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[6][1] > 0);
				chorePlanId[6][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[6][2] > 0);
				chorePlanId[6][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[6][3] > 0);
				chorePlanId[6][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[6][4] > 0);
			}
		}

		// 5. players start to play their chores by calling startChore
		for (int i = 0; i < 7; i++) {
			if (i == 0) {
				for (int j = 0; j < 3; j++) {
					if (j == 1) {
						continue;
					}
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}

			} else if (i == 1) {
				for (int j = 0; j < 4; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 2) {
				for (int j = 0; j < 4; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 3) {
				for (int j = 0; j < 7; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 4) {
				for (int j = 0; j < 5; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 5) {
				for (int j = 0; j < 4; j++) {
					if (j == 2) {
						continue;
					}
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 6) {
				for (int j = 0; j < 5; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			}
			i++;
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the above score UCanCash
		// matrix.
		// Player1 = 1, 0, 3
		returnValue = service.stopChore(choreObservedId[0][0], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[0][2], 9, true);
		Assert.assertTrue(returnValue);
		// Player2 = 3, 2, 2, 3
		returnValue = service.stopChore(choreObservedId[1][0], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][1], 6, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][2], 13, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][3], 8, true);
		Assert.assertTrue(returnValue);
		// Player3 = 3, 3, 3, 3
		returnValue = service.stopChore(choreObservedId[2][0], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[2][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[2][2], 3, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[2][3], 8, true);
		Assert.assertTrue(returnValue);
		// Player4 = 2, 1, 2, 2, 2, 2, 2
		returnValue = service.stopChore(choreObservedId[3][0], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][1], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][2], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][3], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][4], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][5], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][6], 15, true);
		Assert.assertTrue(returnValue);
		// Player5 = 2, 3, 1, 2, 2
		returnValue = service.stopChore(choreObservedId[4][0], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][2], 150, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][3], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][4], 5, true);
		Assert.assertTrue(returnValue);
		// Player6 = 3, 3, 0, 3
		returnValue = service.stopChore(choreObservedId[5][0], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[5][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[5][3], 8, true);
		Assert.assertTrue(returnValue);
		// Player7 = 2, 3, 1, 2, 2
		returnValue = service.stopChore(choreObservedId[6][0], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[6][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[6][2], 150, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[6][3], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[6][4], 5, true);
		Assert.assertTrue(returnValue);

		// 7. call calculateWinners to calculate the winners
		returnValue = service.calculateWinners(new Date());
		Assert.assertTrue(returnValue);

		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the king is player4
		players.clear();
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}
		// Player1 = 4
		Assert.assertEquals(players.get(0).getEarnings(), "4");
		Assert.assertEquals(players.get(0).getChampion_count(), 0);
		// Player2 = 10
		Assert.assertEquals(players.get(1).getEarnings(), "10");
		Assert.assertEquals(players.get(1).getChampion_count(), 0);
		// Player3 = 12*4.4
		Assert.assertEquals(players.get(2).getEarnings(), "52.8");
		Assert.assertEquals(players.get(2).getChampion_count(), 1);
		// Player4 = 13*5.5 -- king
		Assert.assertEquals(players.get(3).getEarnings(), "71.5");
		Assert.assertEquals(players.get(3).getChampion_count(), 0);
		// Player5 = 10*.3.3
		Assert.assertEquals(players.get(4).getEarnings(), "33");
		Assert.assertEquals(players.get(4).getChampion_count(), 0);
		// Player6 = 9*1.1
		Assert.assertEquals(players.get(5).getEarnings(), "9.9");
		Assert.assertEquals(players.get(5).getChampion_count(), 0);
		// Player7 = 10*3.3
		Assert.assertEquals(players.get(6).getEarnings(), "33");
		Assert.assertEquals(players.get(6).getChampion_count(), 0);

		// 9. delete all the test data
		deleteAll(service, accountid);

	}

	@Test
	public void testCalculateWinners4ScenarioE() {
		SocialChoringService service = new SocialChoringServiceImpl();
		long[] accountid = new long[3];
		long[][] chorePlanId = new long[7][7];
		long[][] choreObservedId = new long[7][7];
		List<Player> players = new ArrayList<Player>();

		// 1. Create 3 accounts by calling createAccount
		for (int i = 1; i < 4; i++) {
			accountid[i - 1] = service.createAccount("parent_first_name " + i, "parent_last_name " + i, "parent_email@xxx.us" + i, "player_first_name" + i);
			Assert.assertTrue(accountid[i - 1] > 0);
		}
		// 2. add 4 more players to account by calling addNewPlayerToAccount
		// (then we have 7 players in total)
		boolean returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name4");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name5");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name6");
		Assert.assertTrue(returnValue);
		returnValue = service.addNewPlayerToAccount(accountid[2], "player_first_name7");
		Assert.assertTrue(returnValue);

		// 3. some of them are friends, friends matrix as below
		// Player1 Castle
		// Player1 = 1, 0, 3, = 4
		// Player2 = 3, 3, 3, 3, 3 = 15 -- winner
		//
		// Player2 Castle
		// Player1 = 1, 0, 3, = 4
		// Player2 = 3, 3, 3, 3, 3 = 15 -- winner & NOT King. Two members
		// short.Must have 5 in court
		// Player3 = 3, 3, 3, 3 = 12
		//
		// Player3 Castle
		// Player3 = 3, 3, 3, 3 = 12
		// Player2 = 3, 2, 2, 3 = 15 -- winner
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13
		// Player5 = 2, 3, 1, 2, 2, = 10
		//
		// Player4 Castle
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 -- winner & King
		// Player3 = 3, 3, 3, 3 = 12
		// Player5 = 2, 3, 1, 2, 2, = 10
		// Player6 = 0, 1, 2, 3, 0 = 6
		// Player7 = 0, 1, 2, 3, 3 = 9
		//
		//
		// Player5 Castle
		// Player3 = 3, 3, 3, 3 = 12
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 -- winner
		// Player5 = 2, 3, 1, 2, 2, = 10
		//
		// Player6 Castle
		// Player6 = 0, 1, 2, 3, 0 = 6
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 -- winner
		//
		// Player7 Castle
		// Player7 = 0, 1, 2, 3, 3 = 9
		// Player4 = 2, 1, 2, 2, 2, 2, 2 = 13 -- winner

		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}

		returnValue = service.createFriends(new Date(), players.get(0).getId(), players.get(1).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(1).getId(), players.get(2).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(3).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(2).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(4).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(5).getId());
		Assert.assertTrue(returnValue);
		returnValue = service.createFriends(new Date(), players.get(3).getId(), players.get(7).getId());
		Assert.assertTrue(returnValue);

		// 4. add different chores to each player as the above matrix
		for (Player player : players) {
			if (player.getFirst_name().contains("1")) {
				chorePlanId[0][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[0][0] > 0);
				chorePlanId[0][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[0][1] > 0);
				chorePlanId[0][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[0][2] > 0);
			} else if (player.getFirst_name().contains("2")) {
				chorePlanId[1][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[1][0] > 0);
				chorePlanId[1][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[1][1] > 0);
				chorePlanId[1][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[1][2] > 0);
				chorePlanId[1][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[1][3] > 0);
				chorePlanId[1][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[1][4] > 0);
			} else if (player.getFirst_name().contains("3")) {
				chorePlanId[2][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[2][0] > 0);
				chorePlanId[2][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[2][1] > 0);
				chorePlanId[2][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[2][2] > 0);
				chorePlanId[2][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[2][3] > 0);
			} else if (player.getFirst_name().contains("4")) {
				chorePlanId[3][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[3][0] > 0);
				chorePlanId[3][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[3][1] > 0);
				chorePlanId[3][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[3][2] > 0);
				chorePlanId[3][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[3][3] > 0);
				chorePlanId[3][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[3][4] > 0);
				chorePlanId[3][5] = service.createChorePlanForPlayer(player.getId(), 6);
				Assert.assertTrue(chorePlanId[3][5] > 0);
				chorePlanId[3][6] = service.createChorePlanForPlayer(player.getId(), 7);
				Assert.assertTrue(chorePlanId[3][6] > 0);
			} else if (player.getFirst_name().contains("5")) {
				chorePlanId[4][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[4][0] > 0);
				chorePlanId[4][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[4][1] > 0);
				chorePlanId[4][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[4][2] > 0);
				chorePlanId[4][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[4][3] > 0);
				chorePlanId[4][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[4][4] > 0);
			} else if (player.getFirst_name().contains("6")) {
				chorePlanId[5][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[5][0] > 0);
				chorePlanId[5][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[5][1] > 0);
				chorePlanId[5][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[5][2] > 0);
				chorePlanId[5][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[5][3] > 0);
				chorePlanId[5][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[5][4] > 0);
			} else if (player.getFirst_name().contains("7")) {
				chorePlanId[6][0] = service.createChorePlanForPlayer(player.getId(), 1);
				Assert.assertTrue(chorePlanId[6][0] > 0);
				chorePlanId[6][1] = service.createChorePlanForPlayer(player.getId(), 2);
				Assert.assertTrue(chorePlanId[6][1] > 0);
				chorePlanId[6][2] = service.createChorePlanForPlayer(player.getId(), 3);
				Assert.assertTrue(chorePlanId[6][2] > 0);
				chorePlanId[6][3] = service.createChorePlanForPlayer(player.getId(), 4);
				Assert.assertTrue(chorePlanId[6][3] > 0);
				chorePlanId[6][4] = service.createChorePlanForPlayer(player.getId(), 5);
				Assert.assertTrue(chorePlanId[6][4] > 0);
			}
		}

		// 5. players start to play their chores by calling startChore
		for (int i = 0; i < 7; i++) {
			if (i == 0) {
				for (int j = 0; j < 3; j++) {
					if (j == 1) {
						continue;
					}
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}

			} else if (i == 1) {
				for (int j = 0; j < 5; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 2) {
				for (int j = 0; j < 4; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 3) {
				for (int j = 0; j < 7; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 4) {
				for (int j = 0; j < 5; j++) {
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			} else if (i == 5) {
				for (int j = 0; j < 5; j++) {
					if (j == 0 || j == 4) {
						continue;
					}
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
				}
			} else if (i == 6) {
				for (int j = 0; j < 5; j++) {
					if (j == 0) {
						continue;
					}
					choreObservedId[i][j] = service.startChore(chorePlanId[i][j], 1, new Date());
					Assert.assertTrue(choreObservedId[i][j] > 0);
				}
			}
			i++;
		}

		// 6. players stop their chores by calling stopChore, by passing in
		// approperiate parameters to achieve the above score UCanCash
		// matrix.

		// Player1 = 1, 0, 3
		returnValue = service.stopChore(choreObservedId[0][0], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[0][2], 2, true);
		Assert.assertTrue(returnValue);
		// Player2 = 3, 3, 3, 3, 3
		returnValue = service.stopChore(choreObservedId[1][0], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][2], 3, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][3], 8, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[1][4], 2, true);
		Assert.assertTrue(returnValue);
		// Player3 = 3, 3, 3, 3
		returnValue = service.stopChore(choreObservedId[2][0], 2, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[2][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[2][2], 3, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[2][3], 8, true);
		Assert.assertTrue(returnValue);
		// Player4 = 2, 1, 2, 2, 2, 2, 2
		returnValue = service.stopChore(choreObservedId[3][0], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][1], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][2], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][3], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][4], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][5], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[3][6], 15, true);
		Assert.assertTrue(returnValue);
		// Player5 = 2, 3, 1, 2, 2
		returnValue = service.stopChore(choreObservedId[4][0], 5, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][1], 4, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][2], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][3], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[4][4], 5, true);
		Assert.assertTrue(returnValue);
		// Player6 = 0, 1, 2, 3, 0
		returnValue = service.stopChore(choreObservedId[5][1], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[5][2], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[5][3], 8, true);
		Assert.assertTrue(returnValue);
		// Player7 = 0, 1, 2, 3, 3
		returnValue = service.stopChore(choreObservedId[6][1], 100, false);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[6][2], 15, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[6][3], 8, true);
		Assert.assertTrue(returnValue);
		returnValue = service.stopChore(choreObservedId[6][4], 2, true);
		Assert.assertTrue(returnValue);

		// 7. call calculateWinners to calculate the winners
		returnValue = service.calculateWinners(new Date());
		Assert.assertTrue(returnValue);

		// 8. retrieve each of the players by calling getPlayersForAccount to
		// check if the king is player4
		players.clear();
		for (int i = 1; i < 4; i++) {
			players.addAll(service.getPlayersForAccount(accountid[i]));
		}
		// Player1 = 4
		Assert.assertEquals(players.get(0).getEarnings(), "4");
		Assert.assertEquals(players.get(0).getChampion_count(), 0);
		// Player2 = 15
		Assert.assertEquals(players.get(1).getEarnings(), "15");
		Assert.assertEquals(players.get(1).getChampion_count(), 0);
		// Player3 = 12*4.4
		Assert.assertEquals(players.get(2).getEarnings(), "52.8");
		Assert.assertEquals(players.get(2).getChampion_count(), 1);
		// Player4 = 13*5.5 -- king
		Assert.assertEquals(players.get(3).getEarnings(), "71.5");
		Assert.assertEquals(players.get(3).getChampion_count(), 0);
		// Player5 = 10*.3.3
		Assert.assertEquals(players.get(4).getEarnings(), "33");
		Assert.assertEquals(players.get(4).getChampion_count(), 0);
		// Player6 = 6*.1.1
		Assert.assertEquals(players.get(5).getEarnings(), "6.6");
		Assert.assertEquals(players.get(5).getChampion_count(), 0);
		// Player7 = 9*2.2
		Assert.assertEquals(players.get(6).getEarnings(), "19.8");
		Assert.assertEquals(players.get(6).getChampion_count(), 0);

		// 9. delete all the test data
		deleteAll(service, accountid);

	}

	private void deleteAll(SocialChoringService service, long[] accountid) {
		for (int i = 0; i < accountid.length; i++) {
			service.deleteAccount(accountid[i]);
		}

	}
}
