package com.example.app.items;

import java.util.ArrayList;

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
}
