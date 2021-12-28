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
	
	int affectPlayer(Player p, ArrayList<Player> players, Square start) {
		int r = 0;
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		switch(effectID) {
		case 0:
			return 0;
			
		case 1: //getItem
			r = random.nextInt(5);
			p.addItem(r);
			return 0;
			
		case 2: //goFoward
			r = random.nextInt(3);
			return r+1;
			
		case 3: //backFoward
			r = random.nextInt(2);
			return -r-1;
			
		case 4: //crewRestart
			for(Player op : players) {
				op.setPos(start);
			}
			return 0;
			
		case 5: //crewTakeItem
			for(Player op : players) {
				op.deleteItem(0);
			}
			return 0;
			
		case 6: //crewGiveItem	
			for(Player op : players) {
				r = random.nextInt(5);
				op.addItem(r);
			}
			return 0;
			
		case 7: //振り出しに戻る
			p.setPos(start);
			return 0;
		}
		return 0;
	}
	
}