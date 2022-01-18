package com.example.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import com.example.app.items.BaseItem;

public class Game {
	String gameID = "";
	ArrayList<Player> players = new ArrayList<>();
	HashMap<String, Player> playersMap;
	ArrayList<User> users = new ArrayList<>();
	ArrayList<Player> rank = new ArrayList<>();
	HashMap<String, Boolean> restart;
	Player winPlayer;
	GameMap map;
	int turn;
	boolean effectDone;
	int dice;
	boolean isFinished = false;
	
	public Game(String gID, ArrayList<User> userList){
		this.gameID = gID;
    this.playersMap = new HashMap<>();
		
		//ユーザーリストを読み込み
		users = userList;
		
		//プレイヤーリスト作成
		for(User user : userList) {
			//プレイヤーを作成
			Player player = new Player(user.getID());
			this.players.add(player);
      this.playersMap.put(user.getID(), player);
		}
		restart = new HashMap<>();
		//順番を決定
		setOrder();

		//map初期化
		this.map = new GameMap();
		this.turn = -1;//初期値は-1
		
		
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
	
	Player getNowPlayer() {
		return players.get(turn);
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
	    
	    //JSON用 Map 初期化
	    Map<String, Object> jsonMap = new HashMap<String, Object>();
	    jsonMap.put("Result", "ROLL_DICE");
	    jsonMap.put("Roll", dice);
	    jsonMap.put("Effect", 0);
	    jsonMap.put("Value", 0);
	    //ゴールした時の処理
	    if (targetPlayer.getGoalFlag()) {
	      this.winPlayer = targetPlayer;
	      this.isFinished = true;
	    }
		//分岐入った場合分岐json送信
	    if (remainNum > 0) {
	    	targetPlayer.setMoveRemainNum(remainNum);
	    	//json送信処理
	    	jsonMap.put("NextDiceNum", remainNum);
	    	// JSON送信部分(JSON送信用関数にjsonMapを渡してJSON Objectを生成)
	    	String jsonStr = generateJSON(jsonMap);
	    	jsonMap.put("square",targetPlayer.getPos().getNumber());
	    	sendToAllUsers(jsonStr);
	    	return 1;
	    } 
	    
	    if (!this.effectDone) {
	    	//マスの効果発動
	    	Map<String, Integer> effectResult = squareEffect(targetPlayer);
	    	int effect = effectResult.get("Effect");
	    	int value = effectResult.get("Value");
	    	//進むor戻る効果だった場合コマ移動
			    if (effect == 2 || effect == 3) {
				    targetPlayer.move(value);
            if (effect == 3) {
              value *= -1;
            }
			    }
	    	jsonMap.put("NextDiceNum", remainNum);
	    	jsonMap.put("Effect", effect);
	    	//valueがマイナスならプラスに直して送信
	    	if(value>0) {
	    		jsonMap.put("Value", value);
	    	}else {
	    		jsonMap.put("Value", -value);
	    	}
	    	
	    	if (targetPlayer.getGoalFlag()) {
	    		this.winPlayer = targetPlayer;
	    		this.isFinished = true;
	    	}
	    }
	    jsonMap.put("square",targetPlayer.getPos().getNumber());
	    // JSON送信部分(JSON送信用関数にjsonMapを渡してJSON Objectを生成)
	    sendToAllUsers(generateJSON(jsonMap));

	    //ゴールした場合
	    if (this.isFinished) {
	    	endMatch();
	    }
	    //次ターンにする
	    takeNextTurn();

	    return 0;
	}
	
	void selectRoute(int way) {
		Player targetPlayer = this.players.get(this.turn);
		Square pos = targetPlayer.getPos();
		
		int remainNum = targetPlayer.getMoveRemainNum();

	    //JSON用 Map 初期化
	    Map<String, Object> jsonMap = new HashMap<String, Object>();
	    jsonMap.put("Result", "SELECT_ROUTE");
	    jsonMap.put("Route",way);
	    jsonMap.put("Roll", remainNum);
	    jsonMap.put("NextDiceNum", 0);
	    jsonMap.put("Effect", 0);
	    jsonMap.put("Value", 0);
	    
		if(way == 1) {
			targetPlayer.setPos(pos.next1);
		}else {
			targetPlayer.setPos(pos.next0);
		}
		remainNum--;
	
			//駒移動
			targetPlayer.move(remainNum);
	    if (!this.effectDone) {
				//マスの効果発動
		    	Map<String, Integer> effectResult = squareEffect(targetPlayer);
		    	int effect = effectResult.get("Effect");
		    	int value = effectResult.get("Value");
				//進むor戻る効果だった場合コマ移動
			    if (effect == 2 || effect == 3) {
				    targetPlayer.move(value);
            if (effect == 3) {
              value *= -1;
            }
			    }
		    	jsonMap.put("Effect", effect);
		    	//valueがマイナスならプラスに直して送信
		    	if(value>0) {
		    		jsonMap.put("Value", value);
		    	}else {
		    		jsonMap.put("Value", -value);
		    	}
		    	
		    	if (targetPlayer.getGoalFlag()) {
		    		this.winPlayer = targetPlayer;
	          this.isFinished = true;
		    	}
	    }
	    jsonMap.put("square",targetPlayer.getPos().getNumber());
	    // JSON送信部分(JSON送信用関数にjsonMapを渡してJSON Objectを生成)
	    sendToAllUsers(generateJSON(jsonMap));
	
	    //ゴールした場合
	    if (this.isFinished) {
	      endMatch();
	    }
	    
	    if (this.turn == this.players.indexOf(targetPlayer)) {
	    //次ターンにする
	      takeNextTurn();
	    }
	}
	
	Map<String, Integer> squareEffect(Player p) {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
	    Square square = p.getPos();
	    int squareId = square.getEffectID();
	    resultMap.put("Effect", squareId);
	    int effectResult = square.affectPlayer(p, this.players, this.map.getstart());
	    this.effectDone = true;
	    resultMap.put("Value", effectResult);

	    return resultMap;
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
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("Result", "END_GAME");
		try {
			jsonMap.put("Username", this.winPlayer.getUserID());
		}catch(java.lang.NullPointerException e) {
			jsonMap.put("Username", "none");
		}
		// JSON送信部分(JSON送信用関数にjsonMapを渡してJSON Objectを生成)
		sendToAllUsers(generateJSON(jsonMap));
	}
	
	void lineDropedEndMatch(User user) {
		users.remove(user);
		endMatch();
	}

	
	void restartGame() {
		this.players = new ArrayList<>();
		this.restart = new HashMap<>();
		this.playersMap = new HashMap<>();
		for (User user: this.users) {
			Player player = new Player(user.getID());
			this.players.add(player);
      this.playersMap.put(user.getID(), player);
		}
		//順番を決定
		setOrder();
		
		//map初期化
		this.map = new GameMap();
		this.turn = 0;
		this.winPlayer = null;

		
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("Result", "RESTART_GAME");
		jsonMap.put("Status", true);
    // JSON送信部分(JSON送信用関数にjsonMapを渡してJSON Objectを生成)
    sendToAllUsers(generateJSON(jsonMap));
	}
	
	void takeNextTurn() {
		if(this.turn == this.players.size() - 1)   {
			this.turn = 0 ;
		}else {
			this.turn++;
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("Result", "NEXT_TURN");
		jsonMap.put("Username", this.players.get(this.turn).getUserID());
    // JSON送信部分(JSON送信用関数にjsonMapを渡してJSON Objectを生成)
		sendToAllUsers(generateJSON(jsonMap));
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


  boolean getIsFinished() {
    return this.isFinished;
  }

  String generateJSON(Map<String, Object> jsonMap) {
    JSONObject jo = new JSONObject(jsonMap);
    return jo.toString();
  }

  void sendToAllUsers(String message) {
    this.users.forEach(user -> {
      ComManeger.sendMessage(user.getSession(), message);
    });
  }

  void sendToUser(User user, String message) {
    ComManeger.sendMessage(user.getSession(), message);
  }
  
  void voteRestart(String UID, boolean res) {
	  if(!restart.containsKey(UID)) {
		  restart.put(UID, true);
	  }
	  if(restart.size() == players.size()) {
		  restartGame();
	  }
  }
  public String toString() {
	  String res;
	  res = gameID;
	  for(int i=0;i<users.size();i++) {
		  res += "\n" + users.get(i).toString();
	  }
	  return res;
	  
  }

  public Player getPlayer(String userID) {
    return this.playersMap.get(userID);
  }
}
