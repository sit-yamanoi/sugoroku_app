package com.example.app;

import java.util.ArrayList;
import java.util.Random;

public class Square {
	Square prev0 = null;
	Square prev1 = null;
	Square next0 = null;
	Square next1 = null;
	int effectID = 0; //0:何もなし 1:getItem 2:goFoward 3:backFoward 4:crewRestart 5:crewTakeItem 6:crewGiveItem 7:振り出し
	
	
	Square(int id) {
		effectID = id;
	}
	
	int getEfectID() {
		return effectID;
	}
	
	String affectPlayer(Player p, ArrayList<Player> players, Square start) {
		int s = players.size();
		int i;
		int r = 0;
		Random random = new Random();
		switch(effectID) {
		case 1: //getItem
			r = random.nextInt(7);
			p.getItem(r);
			
		case 2: //goFoward
			r = random.nextInt(3);
			//TODO 進む処理はどこに
			p.moveForward(r+1);
			
		case 3: //backFoward
			//TODO playerクラスの戻る動作の定義
			//TODO 戻る数
			r = random.nextInt(3);
			p.moveBackword(r+1);
			
		case 4: //crewRestart
			for(i = 0; i < s; i++) {
				players.get(i).setPos(start);
			}
			
		case 5: //crewTakeItem
			
			for(i = 0; i < s; i++) {
				players.get(i).deleteItem(0);
			}			
			
		case 6: //crewGiveItem	
			for(i = 0; i < s; i++) {
				r = random.nextInt(7);
				players.get(i).getItem(0);
			}				
		}
		
		return "";
	}
	
}