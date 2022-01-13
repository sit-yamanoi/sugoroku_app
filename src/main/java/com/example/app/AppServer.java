package com.example.app;

import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.Session;

import org.glassfish.tyrus.server.Server;


public class AppServer implements Runnable{

	/* サーバ側のサンプルプログラム
	 * このメインメソッドの例ではサーバインスタンスの生成と起動のみを行っている．
	 */

	static String contextRoot = "/app";
	static String protocol = "ws";
	static int port = 8080;
	HashMap<String,User> UserList = new HashMap<>();
	HashMap<String,User> NoConectedUsers = new HashMap<>();
	HashMap<String,Game> GameList = new HashMap<>();


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
		
		//NoConnectedUsersに追加
		for(int i=0;i<4;i++) {
			appServer.NoConectedUsers.put(testUserID[i],new User(testUserID[i],testGameID));
		}
		
		ArrayList<User> users = new ArrayList<>();
		
		for(int i=0;i<4;i++) {
			users.add(appServer.NoConectedUsers.get(testUserID[i]));
		}
		Game testGame = new Game(testGameID,users);
		appServer.GameList.put(testGameID, testGame);
		
		//テスト出力
		System.out.println("test print");
		Game currentGame = appServer.GameList.get(testGameID);
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
    	while(true) {
    		//キューからメッセージを取得
	    	Message message = ComManeger.deq();
	    	//キューになにもないときはつレッドを停止する
	    	if(message == null)
			{
				System.out.println("wait");
				try {
				    wait();
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
				System.out.println("notify");
				continue;
			}
	    	
	        String request = null;//初期化
	        try {
	        	request = message.getData().getString("Request");
	        }catch(org.json.JSONException e) {
				System.out.println("not reqest");
				return;
			}
	        
			User currentUser;
			Game currentGame;
			Session session = message.getSession();
	        try {
	        	switch(request){
		        	case "MAKE_GAME":
		        		System.out.println("MG");
		        		/*
		        		 * ゲームのインスタンスを作成
		        		 * ユーザをそこに登録
		        		 */
		        		String LobyID = message.getData().getString("LobyID");
		        		/*
		        		 * 配列のやり方がわからないので未完成
		        		 * 送られてきたユーザのインスタンスを作成し、userID,status,gameIDを設定
		        		 * それらを、使って、Gameクラスのインスタンスを作成
		        		 * 作成できたら、クライアント管理サーバにメッセージを送信
		        		 */
		        			break;
		        	case "JOIN_GAME":
		        		System.out.println("JG");
		        		/*
		        		 * ユーザのSessionを設定しUserListに追加
		        		 * ゲームの情報を送信
		        		 */
		        		User currectUser;
		        		String username = message.getData().getString("username");
		        		if(NoConectedUsers.containsKey(username)) {
		        			currentUser = NoConectedUsers.get(username);
		        			currentUser.setSession(session);
		        			UserList.put(session.getId(),currentUser);
		        			NoConectedUsers.remove(username);
		        			//ゲーム情報送信
		        		}else {
		        			//登録済みではないことを通信
		        		}
		        		break;
		        	case "RESTART_GAME":
		        		/*
		        		 * 再戦処理
		        		 */
		        		currentUser = UserList.get(session.getId());
		        		currentGame = GameList.get(currentUser.getGameID());
		        		boolean res; //Jsonから取得
		        		if(res) {
		        			currentGame.voteRestart(currentUser, res);//全員が同意したかはGameクラスで判別
		        		}else {
		        			if(currentGame.getIsFinished()) {
		        				//TODO endGameって何?
		        			currentGame.endGame();
		        			GameList.remove(currentUser.getGameID());
		        			currentGame = null;
		        			}
		        		}
		        		break;
		        	case "EXIT_GAME":
		        		/*
	               * ゲーム終了
		        		 */
		        		currentUser = UserList.get(session.getId());
		        		currentGame = GameList.get(currentUser.getGameID());
		        		//TODO
		        		currentGame.endGame();//全員が同意したかはGameクラスで判別?
		        		break;
		        	case "USE_ITEM":
		        		System.out.println("UI");
		        		/*
		        		 * アイテム使用処理
		        		 */
		        		currentUser = UserList.get(session.getId());
		        		currentGame = GameList.get(currentUser.getGameID());
		        		int position = message.getData().getInt("Position");
		        		int value = message.getData().getInt("value");
		        		
		        		//TODO Playerの取得法が不明
		        		currentGame.useItem(Player,position,value);
		        		
		        		break;
		        	case "ROLL_DICE":
		        		System.out.println("RD");
		        		/*
		        		 * サイコロを降る処理
		        		 * mainProcessを呼び出す
		        		 */
		        		currentUser = UserList.get(session.getId());
		        		currentGame = GameList.get(currentUser.getGameID());
		        		
		        		//User情報は渡さなくていいの?
		        		//そのユーザの手番かどうかはAppServerクラスが判別?
		        		currentGame.mainProcess();
		        		break;
		        	case "SELECT_ROUTE":
		        		System.out.println("SR");
		        		/*
		        		 * 分岐点選択を行う
		        		 */
		        		
		        		currentUser = UserList.get(session.getId());
		        		currentGame = GameList.get(currentUser.getGameID());
		        		
		        		int route = message.getData().getInt("Route");
		        		//Playerの取得方法が不明
		        		currentGame.selectRoute(null,route );
		        		
		        		break;
		        	case "SEND_CHAT":
		        		/*
		        		 * チャットを送信
		        		 * 未設定
		        		 */
		        		System.out.println("SC");
		        		break;
		        	/*	
		        	case "END_GAME":
		        		System.out.println("EG");
		        		 //ゲーム終了の処理
		        		currentUser = UserList.get(session.getId());
		        		currentGame = GameList.get(currentUser.getGameID());
		        		
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
