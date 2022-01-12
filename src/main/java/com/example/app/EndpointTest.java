/*
EndPointSample.java
サーバー起動後の動作を記述する
 */
package com.example.app;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

// エンドポイントは適宜変更する
@ServerEndpoint("/test")
public class EndpointTest {
	private static ArrayList<Session> Sessions = new ArrayList<>();
	static Queue<Message> queue = new ArrayDeque<>();
	static HashMap<String,User> UserList = new HashMap<>();
	static HashMap<String,User> NoConectedUsers = new HashMap<>();
	static HashMap<String,Game> GameList = new HashMap<>();

	private int privateIncrementTest = 0;
	private static int staticIncrementTest = 0;


    @OnOpen
    public void onOpen(Session session, EndpointConfig ec) {
    	Sessions.add(session);
        System.out.println("[WebSocketServerSample] onOpen:" + session.getId());
    }


    @OnMessage
    public void onMessage(final String message, final Session session) throws IOException {
        System.out.println("[WebSocketServerSample] onMessage from (session: " + session.getId() + ") msg: " + message);
        this.privateIncrementTest++;
        EndpointTest.staticIncrementTest++;
        
        //Messageインスタンスを作成
        Message receivedMessage = new Message(message,session);
		User currentUser;
		Game currentGame;
        
        String request = null;
        try {
        	request = receivedMessage.getData().getString("Request");
        }catch(org.json.JSONException e) {
			System.out.println("not reqest");
			return;
		}
        try {
        	switch(request){
	        	case "MAKE_GAME":
	        		System.out.println("MG");
	        		/*
	        		 * ゲームのインスタンスを作成
	        		 * ユーザをそこに登録
	        		 */
	        		String LobyID = receivedMessage.getData().getString("LobyID");
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
	        		String username = receivedMessage.getData().getString("username");
	        		if(NoConectedUsers.containsKey(username)) {
	        			User currentUser = NoConectedUsers.get(username);
	        			currentUser.setWebSocketID(session);
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
	        		currentGame.restartGame();//全員が同意したかはGameクラスで判別?
	        		break;
	        	case "EXIT_GAME":
	        		/*
               * ゲーム終了
	        		 */
	        		currentUser = UserList.get(session.getId());
	        		currentGame = GameList.get(currentUser.getGameID());
	        		currentGame.endGame();//全員が同意したかはGameクラスで判別?
	        		break;
	        	case "USE_ITEM":
	        		System.out.println("UI");
	        		/*
	        		 * アイテム使用処理
	        		 */
	        		currentUser = UserList.get(session.getId());
	        		currentGame = GameList.get(currentUser.getGameID());
	        		int position = receivedMessage.getData().getInt("Position");
	        		int value = receivedMessage.getData().getInt("value");
	        		
	        		//Playerの取得法が不明
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
	        		
	        		int route = receivedMessage.getData().getInt("Route");
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


    @OnClose
    public void onClose(Session session) {
        System.out.println("[WebSocketServerSample] onClose:" + session.getId());
    	Sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("[WebSocketServerSample] onError:" + session.getId());
    }

	public void sendMessage(Session session, String message) {
		System.out.println("[WebSocketServerSample] sendMessage(): " + message);
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendBroadcastMessage(String message) {
		System.out.println("[WebSocketServerSample] sendBroadcastMessage(): " + message);
		Sessions.forEach(session -> {
			// 非同期送信（async）
			session.getAsyncRemote().sendText(message);
		});
	}
	
	public static Message deq() {
		return queue.poll();
	}
}

