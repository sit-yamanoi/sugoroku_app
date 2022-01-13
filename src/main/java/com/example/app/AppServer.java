package com.example.app;

import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.Session;

import org.glassfish.tyrus.server.Server;
import org.json.JSONArray;
import org.json.JSONObject;


public class AppServer implements Runnable{

	/* サーバ側のサンプルプログラム
	 * このメインメソッドの例ではサーバインスタンスの生成と起動のみを行っている．
	 */

	static String contextRoot = "/app";
	static String protocol = "ws";
	static int port = 8080;
	HashMap<String,User> userList = new HashMap<>();
	HashMap<String,User> noConectedUsers = new HashMap<>();
	HashMap<String,Game> gameList = new HashMap<>();


    public static void main(String[] args) throws Exception {
        Server server = new Server(protocol, port, contextRoot, null, ComManeger.class);
        
		AppServer appServer = new AppServer();
		ComManeger.setServer(appServer);
		Thread trd = new Thread(appServer);
		/*
		 * テスト用の、ゲーム、ユーザを作成
		 */
		//ID設定
		String testGameID = "test";
		String testUserID[] = {"user0","user1","user2","user3"};
		
		ArrayList<User> users = new ArrayList<>();
		
		for(int i=0;i<4;i++) {
			users.add(new User(testUserID[i],testGameID));
		}
		
		//NoConnectedUsersに追加
		for(int i=0;i<4;i++) {
			appServer.noConectedUsers.put(testUserID[i],users.get(i));
		}
		
		Game testGame = new Game(testGameID,users);
		appServer.gameList.put(testGameID, testGame);
		
		//テスト出力
		System.out.println("test print");
		Game currentGame = appServer.gameList.get(testGameID);
		System.out.println("gameID = " + currentGame.getGameID());
		users = currentGame.getUserList();
		for(int i=0;i<4;i++) {
			System.out.println(users.get(i));
		}
		
		try {
			System.out.println("---Server Start---");
			server.start();
			trd.start();
			System.in.read();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("---Server Stop---");
			server.stop();
			trd.interrupt();
		}
    }
    
    public void run() {
    	System.out.println("hundle");
    		this.hundleMessage();
    }
    
    
    synchronized public void hundleMessage() {
		User currentUser;
		Game currentGame;
		Message message;
		Session currentSession;
		JSONObject jMessage;
		String request;
		
    	while(true) {
    		//キューからメッセージを取得
	    	message = ComManeger.deq();
	    	//キューになにもないときはつレッドを停止する
	    	if(message == null){
				System.out.println("wait");
				try {
				    wait();
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
				System.out.println("notify");
				continue;
	    	}
	    	
	    	jMessage = message.getData();
	    	currentSession = message.getSession();
	    	
	        try {
	        	request = jMessage.getString("Request");
	        }catch(org.json.JSONException e) {
				System.out.println("not reqest");
				return;
			}
	        
	        try {
	        	switch(request){
		        	case "MAKE_GAME":
		        		System.out.println("MG");
		        		/*
		        		 * ゲームIDの重複チェック
		        		 * ユーザのインスタンス群を作成
		        		 * NoConectedUsersに追加
		        		 * ゲームのインスタンスを作成
		        		 */
		        		String gameID = jMessage.getString("LobyID");	        				
		        		JSONArray jarUserID = jMessage.getJSONArray("UserList");
		        		ArrayList<User> users = new ArrayList<>();
		        		
		        		//gameIDの重複チェック
		        		if(gameList.containsKey(gameID)) {
		        			//TODO エラーメッセージをクライアント管理サーバーに送信
		        			System.out.println("GameIDが重複");
		        			break;
		        		}
		        		
		        		//Userのインスタンス群を作成
		        		for(int i=0;i<jarUserID.length();i++) {
		        			users.add(new User(jarUserID.getString(i),gameID));
		        		}
		        		//NoConectedUsersに追加
		        		for(int i=0;i<jarUserID.length();i++) {
							noConectedUsers.put(jarUserID.getString(i),users.get(i));
						}
		        		
		        		gameList.put(gameID, new Game(gameID,users));
		        		System.out.println("game Maked");
		        		System.out.println(gameList.get(gameID));
		        		break;
		        	case "JOIN_GAME":
		        		System.out.println("JG");
		        		/*
		        		 * ユーザのSessionを設定しUserListに追加
		        		 * ゲームの情報を送信
		        		 */
		        		String username = jMessage.getString("username");
		        		if(noConectedUsers.containsKey(username)) {
		        			currentUser = noConectedUsers.get(username);
		        			currentUser.setSession(currentSession);
		        			userList.put(currentSession.getId(),currentUser);
		        			noConectedUsers.remove(username);
		        			//TODO ゲーム情報送信
		        		}else {
		        			//登録済みではないことを通信
		        		}
		        		break;
		        	case "RESTART_GAME":
		        		/*
		        		 * 再戦処理
		        		 */
		        		/*currentUser = userList..get(currentSession.getId());
		        		currentGame = gameList.get(currentUser.getGameID());
		        		boolean res; //Jsonから取得
		        		if(res) {
		        			currentGame.voteRestart(currentUser, res);//全員が同意したかはGameクラスで判別
		        		}else {
		        			if(currentGame.getIsFinished()) {
		        				//TODO endGameって何?
		        			currentGame.endGame();
		        			gameList.remove(currentUser.getGameID());
		        			currentGame = null;
		        			}
		        		}*/
		        		break;
		        	case "EXIT_GAME":
		        		/*
		        		 * ゲーム終了
		        		 */
		        		/*currentUser = userList.get(currentSession.getId());
		        		currentGame = gameList.get(currentUser.getGameID());
		        		//TODO
		        		currentGame.endGame();//全員が同意したかはGameクラスで判別?*/
		        		break;
		        	case "USE_ITEM":
		        		System.out.println("UI");
		        		/*
		        		 * アイテム使用処理
		        		 */
		        		/*currentUser = userList.get(currentSession.getId());
		        		currentGame = gameList.get(currentUser.getGameID());
		        		int position = jMessage.getInt("Position");
		        		int value = jMessage.getInt("value");
		        		
		        		//TODO Playerの取得法が不明
		        		currentGame.useItem(Player,position,value);*/
		        		
		        		break;
		        	case "ROLL_DICE":
		        		System.out.println("RD");
		        		/*
		        		 * サイコロを降る処理
		        		 * mainProcessを呼び出す
		        		 */
		        		/*currentUser = userList.get(currentSession.getId());
		        		currentGame = gameList.get(currentUser.getGameID());
		        		
		        		//User情報は渡さなくていいの?
		        		//そのユーザの手番かどうかはAppServerクラスが判別?
		        		currentGame.mainProcess();*/
		        		break;
		        	case "SELECT_ROUTE":
		        		System.out.println("SR");
		        		/*
		        		 * 分岐点選択を行う
		        		 */
		        		
		        		currentUser = userList.get(currentSession.getId());
		        		currentGame = gameList.get(currentUser.getGameID());
		        		
		        		int route = jMessage.getInt("Route");
		        		//TODO Playerの取得方法が不明
		        		currentGame.selectRoute(null,route );
		        		
		        		break;
		        	case "SEND_CHAT":
		        		/*
		        		 * チャットを送信
		        		 * 未設定
		        		 */
		        		currentUser = userList.get(currentSession.getId());
		        		currentGame = gameList.get(currentUser.getGameID());
		        		System.out.println("SC");
		        		break;
		        	/*	
		        	case "END_GAME":
		        		System.out.println("EG");
		        		 //ゲーム終了の処理
		        		currentUser = userList.get(session.getId());
		        		currentGame = gameList.get(currentUser.getGameID());
		        		
		        		if(currentGame.getIsFinished()) {
		        			
		        		}
		        		break;
		        	*/
		        	default:
		        		System.out.println("don't defined request : " + request);
		        }
	        }catch(org.json.JSONException e) {
	        	System.out.println("ERROR");
				System.out.println(e);
				System.out.println("request : "+ request);
				return;
			}
    	}
    }
    synchronized public void nft() {
    	this.notify();
    }
}
