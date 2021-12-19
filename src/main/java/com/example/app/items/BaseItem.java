package com.example.app.items;

//
public class BaseItem {
  private int itemID;
  
  public BaseItem(){
	  itemID = 0;
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
