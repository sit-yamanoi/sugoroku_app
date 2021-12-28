package com.example.app.items;

import java.util.ArrayList;

import com.example.app.Player;

public class ItemDefence extends BaseItem {

	public ItemDefence() {
		this.setItemID(2);
	}
	
	public String use(int value , Player p1 , ArrayList<Player> playerList) {
		String result = "ItemDefence";
		p1.setState(4);
		result += ",done";
		return result;
	}
}
