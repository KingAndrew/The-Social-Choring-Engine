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
	public void testCalculateWinners() {
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
//			List<Player_chore_observed> choreObserves = service
//					.getChoreObservedByChorePlanId(player.getId());
//			for (Player_chore_plan plan : chorePlans) {
//				returnValue = service.stopChore(chore_observed_id, time_stoped,
//						did_complete);
//				Assert.assertTrue(returnValue);
//			}
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

	}
}
