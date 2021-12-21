package com.example.app;

import java.util.ArrayList;

import com.example.app.items.BaseItem;

public class Player {
	private String userID;
	private ArrayList<BaseItem> items;
	private int state;
	private Square position;
	private int nextDiceNum;
	private boolean goalFlag;

	public Player(String userID) {
		this.userID = userID;
		this.items = new ArrayList<>();
		this.state = 0;
		this.nextDiceNum = 1;
		this.goalFlag = false;
	}

	public String getUserID() {
		return this.userID;
	}

	public BaseItem getItem(int pos) {
		BaseItem item = null;
		if (this.items.size() > pos) {
			item = this.items.get(pos);
		}
		return item;
	}

	public void setState(int state) {
		if (state <= 4) {
			this.state = state;
		}
	}

	public int getState() {
		return this.state;
	}

	public Square getPos() {
		return this.position;
	}

	public void setPos(Square position) {
		this.position = position;
	}

	public void setNextDiceNum(int num) {
		if (num > 0) {
			this.nextDiceNum = num;
		}
	}

	public int getNextDiceNum() {
		return this.nextDiceNum;
	}

	public int move(int num) {
		int i;
		for(i = num; i > 0; i--) {
			if (this.position.next1 != null) {
				break;
			}
			this.moveForward();
		}
		return i;
	}
	
	public void moveForward() {
		this.position = this.position.next0;
	}
	
	public void moveBackward() {
		this.position = this.position.prev0;
	}

	public void addItem(BaseItem item) {
		this.items.add(item);
	}

	public void deleteItem(int pos) {
		this.items.remove(pos);
	}

	public void setGoalFlag(boolean flag) {
		this.goalFlag = flag;
	}

	public boolean getGoalFlag() {
		return this.goalFlag;
	}
}
