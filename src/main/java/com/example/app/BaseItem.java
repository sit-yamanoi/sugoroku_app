package com.example.app;

public class BaseItem {
  private int itemID;
  
  BaseItem(){
	  itemID = -1;
  }

  public int getItemID() {
	  return itemID;
  }
  public String use() {
	  return "BaseItem";
  }
  
  public String toString() {
	  return "itemID : "+itemID+"\nresrut : "+use();
  }
}
