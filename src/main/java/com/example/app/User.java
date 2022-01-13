package com.example.app;
import javax.websocket.Session;

public class User {
	String userID = "";
	Session mySession;
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
		return "userID : "+userID+"\nwebSocketID : "+ mySession.getId() +"\nstatus : "+status+"\ngameID : "+gameID;
	}
}
