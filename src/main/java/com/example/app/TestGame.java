package com.example.app;

import java.util.ArrayList;

public class TestGame {
	public static void main(String[] args) {
		User user0 = new User("Alice", "1234");
		System.out.println("UserID:" + user0.getID() + " gameID:" + user0.getGameID());
		User user1 = new User("Bob", "1234");
		System.out.println("UserID:" + user1.getID() + " gameID:" + user1.getGameID());
		User user2 = new User("Chris", "1234");
		System.out.println("UserID:" + user2.getID() + " gameID:" + user2.getGameID());
		User user3 = new User("Donald", "1234");
		System.out.println("UserID:" + user3.getID() + " gameID:" + user3.getGameID());
		int rm = 0;
		Player p;
		
		ArrayList<User> userlist = new ArrayList<>();
		userlist.add(user0);
		System.out.println(user0.getID() + " list joined");
		userlist.add(user1);
		System.out.println(user1.getID() + " list joined");
		userlist.add(user2);
		System.out.println(user2.getID() + " list joined");
		userlist.add(user3);
		System.out.println(user3.getID() + " list joined");
		
		Game game = new Game("1234", userlist);
		System.out.println("newgame:" + game.getGameID());
		System.out.println("GameStart");
		int order = 0;
		while(game.checkGoal()<1) {
			System.out.println(order);
			p = game.players.get(game.turn);
			System.out.println("turn"+game.turn + " player:"+ p.getUserID());
			rm = game.mainProcess();
			System.out.println("dicenum:" + game.dice);
			if(rm != 0) {
				System.out.println("RouteSelect");
				game.selectRoute(0);
				
			}
			if(game.checkGoal() != 0) {
				System.out.println("GOAL!!");
			}
			order++;
		}
	}
}
