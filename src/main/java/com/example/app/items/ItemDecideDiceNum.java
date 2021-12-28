package com.example.app.items;

import java.util.ArrayList;

import com.example.app.Player;

public class ItemDecideDiceNum extends BaseItem {

	public ItemDecideDiceNum() {
		this.setItemID(1);
	}

	@Override
	public String use(int value , Player p1 , ArrayList<Player> playerList) {
		String result = "ItemDecideDiceNum";
		p1.setNextDiceNum(value);
		p1.setState(3);
		result += ",done";
		return result;
	}

}
