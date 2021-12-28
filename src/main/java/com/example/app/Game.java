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
	
	public Game(String gID, ArrayList<User> userList){
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
		//effectDone 初期化
		//さいころ振る
		//駒移動
			//分岐入った場合分岐json送信
		//マスの効果発動
		//effectDone = 1
			//進むor戻る効果だった場合コマ移動
				//分岐到達した場合分岐json送信
		//次ターンにする
	}
	
	void selectRoute(Player p, int way) {
		//駒移動
			//分岐入った場合json送信
		//effectID参照
			//effectID == 0の場合マス効果発動
		
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
