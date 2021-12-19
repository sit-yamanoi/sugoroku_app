package com.example.app;

public class User {
	String userID = "";
	String webSocketID = "";
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
	
	void setWebSocketID(String ID){
		webSocketID = ID;
	}
	
	String getWebSocketID() {
		return webSocketID;
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
		return "userID : "+userID+"\nwebSocketID : "+webSocketID+"\nstatus : "+status+"\ngameID : "+gameID;
	}
}

//test2
//test3