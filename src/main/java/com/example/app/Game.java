package com.example.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.example.app.items.BaseItem;

public class Game {
	String gameID = "";
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<User> users = new ArrayList<>();
	ArrayList<Player> rank = new ArrayList<>();
	GameMap map;
	int turn;
	boolean effectDone;
	int dice;
	
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
		this.map = new GameMap();
		this.turn = 0;
		
		
		//TODO テスト用
		//スタートに配置
		for(Player p : players) {
			p.setPos(map.start);
		}
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
		Collections.shuffle(this.players);
	}
	
	//TODO テスト用に返り値追加, dice変数をグローバルに
	int mainProcess() {
		Player targetPlayer = this.players.get(this.turn);
		//effectDone 初期化
	    this.effectDone = false;
		//さいころ振る
	    dice = rollDice();
		//駒移動
	    int remainNum = targetPlayer.move(dice);
		//分岐入った場合分岐json送信
	    if (remainNum > 0) {
			//json送信処理
	    	return 1;
	    } 

	    if (!this.effectDone) {
			//マスの効果発動
	    	squareEffect(targetPlayer);
	    }

		//次ターンにする
	    takeNextTurn();
	    return 0;
	}
	
	void selectRoute(Player p, int way) {
		Square pos = p.getPos();
		if(way == 1) {
			p.setPos(pos.next1);
		}else {
			p.setPos(pos.next0);
		}
		int remainNum = p.getMoveRemainNum();
		//駒移動
		p.move(remainNum);
	    if (!this.effectDone) {
			//マスの効果発動
	    	squareEffect(p);
	    }
	    
	    if (this.turn == this.players.indexOf(p)) {
			//次ターンにする
		    takeNextTurn();
	    }
	}
	
	void squareEffect(Player p) {
	    Square square = p.getPos();
	    int effectResult = square.affectPlayer(p, this.players, this.map.getstart());
	    this.effectDone = true;
		//進むor戻る効果だった場合コマ移動
	    if (effectResult != 0) {
		    int remainNum = p.move(effectResult);
			//分岐入った場合分岐json送信
		    if (remainNum > 0) {
				//json送信処理
		    } 
	    }
	}
	
	int rollDice() {
		Random random = new Random();
		int result = random.nextInt(6);
		result++;
		return result;
	}
	
	void useItem(Player p, int pos, int value) {
		BaseItem item = p.getItem(pos);
		item.use(value, p, this.players);
		p.deleteItem(pos);
	}
	
	void displayResult() {
		//json送信処理
	}
	
	void endMatch() {
		//json送信処理
	}

	
	void restartGame() {
		this.players = new ArrayList<>();
		
		for (User user: this.users) {
			Player player = new Player(user.getID());
			this.players.add(player);
		}
		//順番を決定
		setOrder();
		
		//map初期化
		this.map = new GameMap();
		this.turn = 0;
	}
	
	void takeNextTurn() {
		if(this.turn == this.players.size() - 1)   {
			this.turn = 0 ;
		}else {
			this.turn++;
		}
	}
	
	boolean castChat(String str) {
		return true;
	}
	
	void addRank(Player player) {
		this.rank.add(player);
	}

	//TODO テスト用
	int checkGoal() {
		int g = 0;
		for(Player p : players) {
			if(p.getGoalFlag()) {
				g++;
			}
		}
		return g;
	}
}
