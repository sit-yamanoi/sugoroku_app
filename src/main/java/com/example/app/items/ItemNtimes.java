package com.example.app.items;

import java.util.ArrayList;

import com.example.app.Player;

public class ItemNtimes extends BaseItem {

	public ItemNtimes() {
		this.setItemID(4);
	}

	@Override
	public String use(int value , Player p1 , ArrayList<Player> playerList) {
		String result = "ItemNtimes";
		p1.setState(1);
		
		result += ",done";
		return result;
	}

}
