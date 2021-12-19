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
	
	String affectPlayer(Player p) {
		switch(effectID) {
		case 0: //getItem
			
		case 1: //goFoward
			
		case 2: //backFoward
			
		case 3: //crewRestart
			
		case 4: //crewTakeItem
			
		case 5: //crewGiveItem
			
		}
		
		return "";
	}
	
}