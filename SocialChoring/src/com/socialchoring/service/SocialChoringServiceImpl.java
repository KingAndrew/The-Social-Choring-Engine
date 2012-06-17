package com.socialchoring.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.socialchoring.bean.Player;
import com.socialchoring.bean.Player_chore_plan;

public class SocialChoringServiceImpl implements SocialChoringService {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/tsce";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "test1234";

	@Override
	public String login(String parentEmail, String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long createAccount(String parent_first_name,
			String parent_last_name, String parent_email,
			String player_first_name) {

		Connection dbConnection = null;
		CallableStatement callableStatement = null;

		String createAccountStoreProc = "{call create_account(?,?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection
					.prepareCall(createAccountStoreProc);

			callableStatement.setString(1, parent_first_name);
			callableStatement.setString(2, parent_last_name);
			callableStatement.setString(3, parent_email);
			callableStatement.setString(4, player_first_name);

			// execute insertDBUSER store procedure
			callableStatement.executeQuery();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		// TODO
		return 0;
	}

	@Override
	public boolean validateAccount(long account_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disableAccount(long account_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addNewPlayerToAccount(long account_id,
			String player_first_name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Player> getPlayersForAccount(long account_id) {
		Connection dbConnection = null;
		CallableStatement callableStatement = null;

		String createAccountStoreProc = "{call get_players_for_account(?)}";

		List<Player> players = new ArrayList<Player>();
		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection
					.prepareCall(createAccountStoreProc);

			callableStatement.setLong(1, account_id);

			// execute insertDBUSER store procedure
			ResultSet rs = callableStatement.executeQuery();
			Player player;
			while (rs.next()) {
				player = new Player();
				player.setId(rs.getInt("id"));
				player.setFirst_name(rs.getString("first_name"));
				player.setEarnings(rs.getString("earnings"));
				player.setChampion_count(rs.getInt("champion_count"));
				player.setParent_id(rs.getInt("parent_id"));
				player.setDate_added(rs.getString("date-added"));
				player.setDate_validated(rs.getString("date_validated"));
				player.setVersion(rs.getInt("version"));

				players.add(player);
			}

			return players;
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return players;

	}

	@Override
	public boolean createChorePlanForPlayer(long player_id,
			long chore_plan_to_copy_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createFriends(Date date, long player_one_id,
			long player_two_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFriendsForPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startChore(long chore_plan_id, Date time_started,
			Date date_observed) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopChore(long chore_observed_id, long time_stoped,
			boolean did_complete) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean calculateWinners(Date date_observed) {
		return false;
	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

	@Override
	public List<Player_chore_plan> getChorePlanByPlayerId(long playerId) {
		// TODO Auto-generated method stub
		return null;
	}

}