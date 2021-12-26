package com.example.app.items;

//
abstract class BaseItem {
  private int itemID;
  
  public void setItemID(int n) {
	  itemID = n;
  }
  
  public int getItemID() {
	  return itemID;
  }
  abstract public String use();
  
  public String toString() {
	  return "itemID : "+itemID+"\nresrut : "+use();
  }
}
