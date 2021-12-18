package com.example.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
	private String userId = "";
  private ArrayList<BaseItem> items;
  private int state;
  private Square position;
  private int nextDiceNum;
  private boolean goalFlag;

  public String getUserId() {}

  public BaseItem getItem(int pos) {}

  public void setState(int state) {}

  public int getState() {}

  public Square getPos() {}

  public void setNextDiceNum(int num) {}

  public int getNextDiceNum() {}

  public int moveForward() {}

  public void addItem (BaseItem item) {}

  public void deleteItem(int pos) {}

  public void setGoalFlag() {}

  public boolean getGoalFlag() {}
}
