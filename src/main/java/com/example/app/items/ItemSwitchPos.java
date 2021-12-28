package com.example.app.items;

import java.util.ArrayList;
import java.util.Random;

import com.example.app.Player;
import com.example.app.Square;
public class ItemSwitchPos extends BaseItem {

	public ItemSwitchPos() {
		this.setItemID(5);
	}
	
	@Override
	public String use(int value , Player p1 , ArrayList<Player> playerList) {
		String result = "ItemSwitchPos";
		
		//効果対象者を指定
		Random rand = new Random();
		int n = rand.nextInt(playerList.size());
		Player p2 = playerList.get(n);
		result += ","+n;
		
		//効果無効ではない場合
		if(p2.getState() != 4) {
			Square buf = p1.getPos();
			p1.setPos(p2.getPos());
			p2.setPos(buf);
			result += ",done";
		}
		//効果無効の場合
		else {
			p2.setState(0);
			result += ",defenced";
		}
		return result;
	}

}
