package com.example.app;

public class Square {
	Square prev0 = null;
	Square prev1 = null;
	Square next0 = null;
	Square next1 = null;
	int effectID = 0; //0:何もなし 1:getItem 2:goFoward 3:backFoward 4:crewRestart 5:crewTakeItem 6:crewGiveItem
	
	
	Square(int id) {
		effectID = id;
	}
	
	int getEfectID() {
		return effectID;
	}
	
	String affectuPlayer(Player p) {
		return "";
	}
	
}