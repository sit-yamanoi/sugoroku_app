package com.example.app;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
	String gameID = "";
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<User> users = new ArrayList<>();
	ArrayList<Integer> order = new ArrayList<>();
	ArrayList<Player> rank = new ArrayList<>();
	ArrayList<Map> maps = new ArrayList<>();
	int turn;
	Map nowMap;
	
	public Game(String gID, ArrayList<User> userList){		
		this.gameID = gID;
		
		//ユーザーリストを読み込み
		users = userList;
		
		//プレイヤーリスト作成
		for(User user : userList) {
			//プレイヤーを作成
			Player player = new Player(user.getID());
			this.players.add(player);
		}
		
		//順番を決定
		setOrder();

		//map初期化
		
	}
	
	
	String getGameID() {
		return this.gameID;
	}
	
	ArrayList<Player> getPlayerList() {
		return players;
	}
	
	ArrayList<User> getUserList() {
		return users;
	}
	
	void setOrder(){
		for(int i=0; i<users.size(); i++) {
			this.order.add(i);
		}
		Collections.shuffle(order);
	}
	
	void mainProcess() {
		//さいころ振る
		//駒進移動
			//分岐入った場合selectRoute呼び出し
			//分岐後の進める分移動
		//マスの効果発動
			//進むor戻る効果だった場合コマ移動
				//分岐到達した場合selectRoute呼び出し
		//次ターンにする
	}
	
	void selectRoute(Player p, int way) {
		Square pos = p.getPos();
		if(way == 1) {
			p.setPos(pos.next1);
		}else {
			p.setPos(pos.next0);
		}
	}
	
	void useItem(Player p, int pos) {
		BaseItem item = p.getItem(pos);
		item.use();
		p.deleteItem(pos);
	}
	
	void displayResult() {
		
	}
	
	void endMatch() {
		
	}
	
	void restartGame() {
		
	}
	
	void takeNextTurn() {
		if(this.turn == this.users.size())   {
			this.turn = 1;
		}else {
			this.turn++;
		}
	}
	
	boolean castChat(String str) {
		return true;
	}
	
	void changeMap() {
		
	}
	
	void addRank(Player player) {
		this.rank.add(player);
	}
}
