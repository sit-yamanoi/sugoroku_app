package com.example.app.items;

import java.util.ArrayList;
import java.util.Random;

import com.example.app.Player;
//
public abstract class BaseItem {
  private int itemID;
  
  //value アイテム使用に伴う変数、または効果対象プレイヤーを指定する変数
  abstract public String use(int value , Player p1 , ArrayList<Player> playerList);
  
  public void setItemID(int n) {
	  itemID = n;
  }
  
  public int getItemID() {
	  return itemID;
  }
  
  public String toString() {
	  return "itemID : " + itemID;
  }
  
  public static BaseItem generateItem() {
	  BaseItem item;
	  Random rand = new Random();
	  switch(rand.nextInt(5)) {
	  case(0):
		  item = new ItemDecideDiceNum();
		  break;
	  case(1):
		  item = new ItemDefence();
		  break;
	  case(2):
		  item = new ItemHalf();
		  break;
	  case(3):
		  item = new ItemNtimes();
		  break;
	  default:
		  item = new ItemSwitchPos();
		  break;
	  }
	  return item;
  }
}
