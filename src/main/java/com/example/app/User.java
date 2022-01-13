package com.example.app;
import javax.websocket.Session;

public class User {
	String userID = "";
	Session mySession = null;
	int status;
	String gameID = "";
	
	User(String uID,String gID){
		userID = uID;
		status = 0;
		gameID = gID;
	}
	
	String getID() {
		return userID;
	}
	
	void setSession(Session session){
		mySession = session;
	}
	
	Session getSession() {
		return mySession;
	}
	
	void setGameID(String ID) {
		gameID = ID;
	}
	
	String getGameID() {
		return gameID;
	}
	
	void setStatus(int number) {
		status = number;
	}
	
	int getStatus() {
		return status;
	}
	public String toString() {
		if(mySession == null) {
			return "userID : "+userID+"\nwebSocketID : null \nstatus : "+status+"\ngameID : "+gameID;
		}
		return "userID : "+userID+"\nwebSocketID : "+ mySession.getId() +"\nstatus : "+status+"\ngameID : "+gameID;
	}
}
