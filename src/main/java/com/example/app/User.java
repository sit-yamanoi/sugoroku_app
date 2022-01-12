package com.example.app;
import javax.websocket.Session;

import javax.websocket.Session;

public class User {
	String userID = "";
	Session mysession;
	int status;
	String gameID = "";
	
	User(String uID,String gID, Session session){
		userID = uID;
    this.mySession = session;
		status = 0;
		gameID = gID;
	}
	
	String getID() {
		return userID;
	}
	
	void setSession(Session session){
		mysession = session;
	}
	
	Session getSession() {
		return mysession;
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
		return "userID : "+userID+"\nwebSocketID : "+ mysession.getId() +"\nstatus : "+status+"\ngameID : "+gameID;
	}
}
