package com.example.app;

public class TestMap {

	public static void main(String[] args) {
		GameMap map = new GameMap();
		int i;
		Square sq = map.start;
		for(i = 0; ;i++) {
			System.out.print(i + "/" + sq.effectID + "->");
			if(sq == map.goal) {
				System.out.println("goal");
				break;
			}
			sq = sq.next0;
		}
		
		sq = map.start;
		
		for(i = 0; ;i++) {
			System.out.print(i + "/" + sq.effectID + "->");
			if(sq == map.goal) {
				System.out.println("goal");
				break;
			}
			if(sq.next1 != null) {
				sq = sq.next1;
				System.out.print("分岐");
			}else{
				sq = sq.next0;
			}
		}
		
	}

}
