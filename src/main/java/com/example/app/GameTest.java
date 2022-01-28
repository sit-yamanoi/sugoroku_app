package com.example.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameTest {
	public static void main(String[] args) {
		String testGameID = "test_game_id";
		// テストユーザー初期化
		User user1 = new User("test1", testGameID);
		User user2 = new User("test2", testGameID);
		User user3 = new User("test3", testGameID);
		User user4 = new User("test4", testGameID);

		ArrayList<User> testUsers = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));
		HashMap<String, User> testUsersMap = new HashMap<>();

		for (User user : testUsers) {
			testUsersMap.put(user.userID, user);
		}

		Game testGame = new Game(testGameID, testUsers);

		constructorTest();
		getGameIDTestSuccess(testGame, testGameID);
		getPlayerListTestSuccess(testGame, testUsersMap);
		getNowPlayerTestSuccess(testGame);
		getNowPlayerTestFailed(testGame);
		getUserListTestSuccess(testGame, testUsers);
		rollDiceTestSuccess(testGame);
		getPlayerTestSuccess(testGame, "test1");
		getPlayerTestFailed(testGame);
		squareEffectTest(testGame);
		endMatchTest(testGame);
		takeNextTurnTest(testGame);
		takeNextTurnLastTest(testGame);
		checkGoalTest(testGame);
		getIsFinishedTest(testGame);
	}

	public static void constructorTest() {
		System.out.print("constructorTest ..... ");

		String result = "Success";
		String testGameID = "test_game_id";
		User user1 = new User("test1", testGameID);
		User user2 = new User("test2", testGameID);
		User user3 = new User("test3", testGameID);
		User user4 = new User("test4", testGameID);
		ArrayList<User> testUsers = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));
		Game testGame = new Game(testGameID, testUsers);
		HashMap<String, User> testUsersMap = new HashMap<>();

		for (User user : testUsers) {
			testUsersMap.put(user.userID, user);
		}

		if (testGame.gameID != testGameID) {
			result = "Failed";
		}
		if (testGame.users != testUsers) {
			result = "Failed";
		}

		ArrayList<Player> players = testGame.players;

		for (Player player : players) {
			if (testUsersMap.get(player.getUserID()) == null) {
				result = "Failed";
			}
		}

		if (testGame.restart == null) {
			result = "Failed";
		}
		if (testGame.map == null) {
			result = "Failed";
		}
		if (testGame.turn != -1) {
			result = "Failed";
		}
		System.out.println(result + "\n");
	}

	public static void getGameIDTestSuccess(Game game, String expect) {
		System.out.print("getGameIDTestSuccess ..... ");

		String result = "Success";
		String actual = game.getGameID();
		if (expect != actual) {
			result = "Failed";
		}
		System.out.println(result + "\n");
	}

	public static void getPlayerListTestSuccess(Game game, HashMap<String, User> expectUsers) {
		System.out.print("getPlayerListTestSuccess ..... ");

		String result = "Success";
		ArrayList<Player> players = game.getPlayerList();
		if (players.size() != 4) {
			result = "Failed";
		}
		for (Player player : players) {
			if (expectUsers.get(player.getUserID()) == null) {
				result = "Failed";
			}
		}
		System.out.println(result + "\n");
	}

	public static void getNowPlayerTestSuccess(Game game) {
		System.out.print("getNowPlayerTestSuccess ..... ");

		String result = "Success";
		ArrayList<Player> players = game.getPlayerList();

		game.turn = 0;
		int turn = game.turn;

		if (game.getNowPlayer() != players.get(turn)) {
			result = "Failed";
		}
		System.out.println(result + "\n");
	}

	public static void getNowPlayerTestFailed(Game game) {
		System.out.print("getNowPlayerTestFailed ..... ");

		String result = "Success";
		ArrayList<Player> players = game.getPlayerList();

		game.turn = -1;
		int turn = game.turn;

		if (game.getNowPlayer() != null) {
			result = "Failed";
		}
		System.out.println(result + "\n");
	}

	public static void getUserListTestSuccess(Game game, ArrayList<User> expectUsers) {
		System.out.print("getUserListTestSuccess ..... ");

		String result = "Success";
		ArrayList<User> users = game.getUserList();

		if (expectUsers.size() != users.size()) {
			result = "Failed";
		}

		for (User user : users) {
			if (!expectUsers.contains(user)) {
				result = "Failed";
			}
		}

		System.out.println(result + "\n");
	}

	public static void getPlayerTestSuccess(Game game, String testUserID) {
		System.out.print("getPlayerTestSuccess ..... ");
		String result = "Success";
		Player player = game.getPlayer(testUserID);

		if (!player.getUserID().equals(testUserID)) {
			result = "Failed";
		}

		System.out.println(result + "\n");
	}

	public static void getPlayerTestFailed(Game game) {
		System.out.print("getPlayerTestFailed ..... ");
		String result = "Success";
		String dummyUserID = "dummy";
		Player player = game.getPlayer(dummyUserID);

		if (player != null) {
			result = "Failed";
		}

		System.out.println(result + "\n");
	}

	public static void rollDiceTestSuccess(Game game) {
		System.out.print("rollDiceTestSuccess ..... ");
		String result = "Success";
		int dice = game.rollDice();
		if (dice > 6 || dice < 1) {
			result = "Failed";
		}
		System.out.println(result + "\n");
	}

	public static void squareEffectTest(Game game) {
		System.out.print("squareEffectTest ..... ");
		String result = "Success";
		
		Square squere = new Square(4);
		Player player = game.getPlayerList().get(0);
		player.setPos(squere);
		Map<String, Integer> resultMap = game.squareEffect(player);
		if (resultMap.get("Effect") != 4) {
			result = "Failed";
		}
		if (resultMap.get("Value") != 0) {
			result = "Failed";
		}
		System.out.println(result + "\n");
	}

	public static void mainProcessTest() {
		
	}
	
	public static void endMatchTest(Game game) {
		System.out.println("endMatchTest ..... ");

		game.winPlayer = game.getPlayerList().get(0);
		game.endMatch();
		game.winPlayer = null;
		System.out.println();

		// 以下が標準出力される
		// {"Result": "END_GAME", "Username": "test1"}
		// {"Result": "END_GAME", "Username": "test1"}
		// {"Result": "END_GAME", "Username": "test1"}
		// {"Result": "END_GAME", "Username": "test1"}
		System.out.println();

	}
	
	public static void takeNextTurnTest(Game game) {
		System.out.println("takeNextTurnTest ..... ");

		game.turn = 0;
		game.takeNextTurn();
		System.out.println("turn: " + 1);
		System.out.println("userID: " + game.getPlayerList().get(1).getUserID());
		System.out.println();

		// 以下が標準出力される
		// {"Result": "NEXT_TURN", "Username": "${userID}"}
		// {"Result": "NEXT_TURN", "Username": "${userID}"}
		// {"Result": "NEXT_TURN", "Username": "${userID}"}
		// {"Result": "NEXT_TURN", "Username": "${userID}"}
		// turn: 1
		// userID: ${userID}
	}
	public static void takeNextTurnLastTest(Game game) {
		System.out.println("takeNextTurnLastTest ..... ");

		game.turn = 3;
		game.takeNextTurn();
		System.out.println("turn: " + 0);
		System.out.println("userID: " + game.getPlayerList().get(0).getUserID());
		System.out.println();

		// 以下が標準出力される
		// {"Result": "NEXT_TURN", "Username": "${userID}"}
		// {"Result": "NEXT_TURN", "Username": "${userID}"}
		// {"Result": "NEXT_TURN", "Username": "${userID}"}
		// {"Result": "NEXT_TURN", "Username": "${userID}"}
		// turn: 0
		// userID: ${userID}
	}
	
	public static void checkGoalTest(Game game) {
		System.out.print("checkGoalTest ..... ");
		String result = "Success";
		
		Player player = game.getPlayerList().get(0);
		
		player.setGoalFlag(true);
		
		int goalNum = game.checkGoal();
		if (goalNum != 1) {
			result = "Failed";
		}
		System.out.println(result + "\n");
		player.setGoalFlag(false);
	}
	
	public static void getIsFinishedTest(Game game) {
		System.out.print("getIsFinishedTest ..... ");
		String result = "Success";
		game.isFinished = true;
		
		if (!game.getIsFinished()) {
			result = "Failed";
		}
		System.out.println(result + "\n");
		
		game.isFinished = false;
	}
}
