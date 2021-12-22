package com.example.app;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
	String gameID = "";
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<User> users = new ArrayList<>();
	ArrayList<Integer> order = new ArrayList<>();
	ArrayList<Integer> rank = new ArrayList<>();
	ArrayList<Map> maps = new ArrayList<>();
	
	public Game(String gID;ArrayList<User> userList){
		int i;
		
		this.gameID = gID;
		
		//ユーザーリストを読み込み
		users = userList;
		
		//プレイヤーリスト作成
		for(User user : userList) {
			//プレイヤーを作成
		}
		
		//順番を決定
		setOrder();
		//rank初期化
		for(i=0;i<users.size();i++) {
			rank.add(1);
		}
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
		for(i=0;i<users.size();i++) {
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
	
	void selectRoute(Player p) {
		
	}
	
	void useItem(Player p, int pos) {
		
	}
	
	void displayResult() {
		
	}
	
	void endMatch() {
		
	}
	
	void restartGame() {
		
	}
	
	void takeNextTurn() {
		
	}
	
	boolean castChat(String str) {
		return true;
	}
	
	void changeMap() {
		
	}
	
	void addRank() {
		
	}
}
