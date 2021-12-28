package com.example.app;

import java.util.ArrayList;

import com.example.app.items.BaseItem;


public class Player {
	private String userID;
	private ArrayList<BaseItem> items;
	private int state;
	private Square position;
	private int moveRemainNum;
	private boolean goalFlag;

	public Player(String userID) {
		this.userID = userID;
		this.items = new ArrayList<>();
		this.state = 0;
		this.moveRemainNum = 0;
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
		this.state = state;
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

	public void setMoveRemainNum(int num) {
		if (num > 0) {
			this.moveRemainNum = num;
		}
	}

	public int getMoveRemainNum() {
		return this.moveRemainNum;
	}

	public int move(int num) {
		int i;
		if(num < 0) {
			for(i = num; i < 0; i++) {
				if (this.position.prev1 != null) {
					break;
				}
				this.moveBackward();
			}
		}
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
		if (this.items.size() < 3) {
			this.items.add(item);
		}
	}

	public void deleteItem(int pos) {
		if (this.items.size() - 1 >= pos) {
			this.items.remove(pos);
		}
	}

	public void setGoalFlag(boolean flag) {
		this.goalFlag = flag;
	}

	public boolean getGoalFlag() {
		return this.goalFlag;
	}
}
