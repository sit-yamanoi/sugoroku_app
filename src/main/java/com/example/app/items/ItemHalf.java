package com.example.app.items;

import java.util.ArrayList;

import com.example.app.Player;

public class ItemHalf extends BaseItem {

	public ItemHalf() {
		this.setItemID(2);
	}
	
	public String use(int value , Player p1 , ArrayList<Player> playerList) {
		String result = "ItemHalf";
		
		//効果対象者の指定
		Player p2 = playerList.get(value);
		if(p2.getState() == 4) {
			p2.setState(0);
			result += ",defenced";
		}
		else {
			p2.setState(2);
			result += ",done";
		}
		return result;
	}
}
