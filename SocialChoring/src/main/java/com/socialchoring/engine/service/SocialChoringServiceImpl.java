package com.socialchoring.engine.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.socialchoring.engine.bean.FriendsForDate;
import com.socialchoring.engine.bean.Player;
import com.socialchoring.engine.bean.PlayerChorePlan;

@Component
public class SocialChoringServiceImpl implements SocialChoringService {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/tsce";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "test1234";
	static Connection dbConnection = null;

	@Override
	public boolean login(String userName, long userId, Date date) {
		CallableStatement callableStatement = null;
		String storeProc = "{call login(?,?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setString(1, userName);
			callableStatement.setLong(2, userId);
			callableStatement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			callableStatement.registerOutParameter(4, Types.BOOLEAN);

			callableStatement.execute();
			boolean success = callableStatement.getBoolean(4); // index-based
			return success;

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

		return false;
	}

	@Override
	public long createAccount(String userName, String parent_first_name, String parent_last_name, String parent_email, String player_first_name) {
		CallableStatement callableStatement = null;
		String storeProc = "{call create_account(?,?,?,?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setString(1, userName);
			callableStatement.setString(2, parent_first_name);
			callableStatement.setString(3, parent_last_name);
			callableStatement.setString(4, parent_email);
			callableStatement.setString(5, player_first_name);
			callableStatement.registerOutParameter(6, Types.BIGINT);

			callableStatement.execute();
			long accountId = callableStatement.getLong(6); // index-based

			System.out.println("Record is inserted into DBUSER table with created accountId=" + accountId);

			return accountId;

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

		return 0;
	}

	@Override
	public boolean validateAccount(long account_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disableAccount(long account_id) {
		CallableStatement callableStatement = null;
		String storeProc = "{call disable_account(?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setLong(1, account_id);
			callableStatement.registerOutParameter(2, Types.BOOLEAN);

			callableStatement.execute();
			return callableStatement.getBoolean(2);

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

		return false;
	}

	@Override
	public boolean addNewPlayerToAccount(long account_id, String player_first_name) {
		CallableStatement callableStatement = null;
		String storeProc = "{call add_new_player_to_account(?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setLong(1, account_id);
			callableStatement.setString(2, player_first_name);
			callableStatement.registerOutParameter(3, Types.BOOLEAN);

			callableStatement.execute();
			return callableStatement.getBoolean(3);

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

		return false;
	}

	@Override
	public List<Player> getPlayersForAccount(long account_id) {
		CallableStatement callableStatement = null;
		String storeProc = "{call get_players_for_account(?)}";
		List<Player> players = new ArrayList<Player>();
		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

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
				player.setDate_added(rs.getString("date_added"));
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
	public long createChorePlanForPlayer(long player_id, long chore_plan_to_copy_id) {
		CallableStatement callableStatement = null;
		String storeProc = "{call create_chore_plan_for_player(?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setLong(1, player_id);
			callableStatement.setLong(2, chore_plan_to_copy_id);
			callableStatement.registerOutParameter(3, Types.BIGINT);

			callableStatement.execute();
			return callableStatement.getLong(3);

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

		return 0;
	}

	@Override
	public boolean createFriends(Date date, long player_one_id, long player_two_id) {
		CallableStatement callableStatement = null;
		String storeProc = "{call create_friends_for_date(?,?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setDate(1, new java.sql.Date(new Date().getTime()));
			callableStatement.setLong(2, player_one_id);
			callableStatement.setLong(3, player_two_id);
			callableStatement.registerOutParameter(4, Types.BOOLEAN);

			callableStatement.execute();
			return callableStatement.getBoolean(4);

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

		return false;
	}

	@Override
	public List<FriendsForDate> getFriendsForPlayer(long playerId, Date beginDate) {
		CallableStatement callableStatement = null;
		String storeProc = "{call get_friends_for_player(?,?)}";
		List<FriendsForDate> friends = new ArrayList<FriendsForDate>();
		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setLong(1, playerId);

			// execute insertDBUSER store procedure
			ResultSet rs = callableStatement.executeQuery();
			FriendsForDate friend;
			while (rs.next()) {
				friend = new FriendsForDate();
				friend.setId(rs.getLong("id"));
				friend.setPlayer_one(rs.getInt("player_one"));
				friend.setPlayer_two(rs.getInt("player_two"));
				friend.setBegin_date(rs.getString("begin_date"));
				friends.add(friend);
			}

			return friends;
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
		return friends;

	}

	@Override
	public long startChore(long chore_plan_id, long time_started, Date date_observed) {
		CallableStatement callableStatement = null;
		String storeProc = "{call start_chore(?,?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setLong(1, chore_plan_id);
			callableStatement.setLong(2, time_started);
			callableStatement.setDate(3, new java.sql.Date(date_observed.getTime()));
			callableStatement.registerOutParameter(4, Types.BIGINT);

			callableStatement.execute();
			return callableStatement.getLong(4);

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

		return 0;
	}

	@Override
	public boolean stopChore(long chore_observed_id, long time_stoped, boolean did_complete) {
		CallableStatement callableStatement = null;
		String storeProc = "{call stop_chore(?,?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setLong(1, chore_observed_id);
			callableStatement.setLong(2, time_stoped);
			callableStatement.setBoolean(3, did_complete);
			callableStatement.registerOutParameter(4, Types.BOOLEAN);

			callableStatement.execute();
			return callableStatement.getBoolean(4);

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

		return false;
	}

	@Override
	public boolean calculateWinners(Date date_observed) {
		CallableStatement callableStatement = null;
		String storeProc = "{call calculate_winners(?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setDate(1, new java.sql.Date(date_observed.getTime()));
			callableStatement.registerOutParameter(2, Types.BOOLEAN);

			callableStatement.execute();
			return callableStatement.getBoolean(2);

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

		return false;
	}

	private static Connection getDBConnection() throws SQLException {
		if (dbConnection == null || dbConnection.isClosed()) {
			try {

				Class.forName(DB_DRIVER);

			} catch (ClassNotFoundException e) {

				System.out.println(e.getMessage());

			}
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
		}

		return dbConnection;

	}

	@Override
	public boolean deleteAccount(long acountId) {
		CallableStatement callableStatement = null;
		String storeProc = "{call delete_account(?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setLong(1, acountId);
			callableStatement.registerOutParameter(2, Types.BOOLEAN);

			callableStatement.execute();
			return callableStatement.getBoolean(2);

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

		return false;
	}

	@Override
	public boolean verifyUser(String username) {
		CallableStatement callableStatement = null;
		String storeProc = "{call verify_user(?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setString(1, username);
			callableStatement.registerOutParameter(2, Types.BOOLEAN);

			callableStatement.execute();
			return callableStatement.getBoolean(2);

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

		return false;
	}

	@Override
	public List<PlayerChorePlan> getChoresForPlayerByDate(long playerId, Date date) {
		CallableStatement callableStatement = null;
		String storeProc = "{call get_plan_for_player_by_date(?,?,?)}";
		List<PlayerChorePlan> chorePlans = new ArrayList<PlayerChorePlan>();
		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setLong(1, playerId);

			// execute insertDBUSER store procedure
			ResultSet rs = callableStatement.executeQuery();
			PlayerChorePlan plan;
			while (rs.next()) {
				plan = new PlayerChorePlan();
				plan.setId(rs.getLong("id"));
				plan.setPlayer_id(playerId);
				plan.setWhen(rs.getString("when"));
				plan.setName(rs.getString("name"));
				plan.setDescription(rs.getString("description"));
				plan.setHow_often(rs.getString("how_often"));
				plan.setIdeal_time(rs.getString("ideal_time"));
				plan.setMax_time(rs.getString("max_time"));
				plan.setMin_time(rs.getString("min_time"));
				chorePlans.add(plan);
			}

			return chorePlans;
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
		return chorePlans;
	}

	@Override
	public long getAccountForUser(String username) {
		CallableStatement callableStatement = null;
		String storeProc = "{call get_account_for_user(?,?)}";
		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(storeProc);

			callableStatement.setString(1, username);
			callableStatement.registerOutParameter(2, Types.BIGINT);
			

			callableStatement.execute();
			long accountId = callableStatement.getLong(2); // index-based

			return accountId;
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
		return 0;
	}

}