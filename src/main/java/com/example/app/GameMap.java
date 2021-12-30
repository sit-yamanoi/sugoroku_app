package com.example.app;

public class GameMap {
	Square start = null;
	Square goal = null;
	int[] mapStr; //マップ構造 [分岐スタート地点][合流地点][コマの効果][...]の順 0,1列目は全て0
	int[] mapLen; //ルートの長さ [メインの長さ][分岐の長さ][分岐の長さ]の順
	
	Square getstart() {
		return start;
	}
	
	Square getgoal() {
		return goal;
	}
	
	void genSquare(int mapnum) { //mapnum:マップ番号
		int m = 0; //配列カウント
		int i = 0; //ループ用
		int j = 0; //メインルートマスカウント
		int r = 0; //分岐カウント
		
		int fs, fg; //分岐スタート,ゴール地点のマス目
		Square current = null; //作成したマス
		Square prev = null; //前のマス
		Square forkStart = null; //分岐開始点
		Square forkGoal = null; //分岐終了点
		
		do {
			fs = mapStr[m];
			m++;
			fg = mapStr[m];
			m++;
			
			
			if(fs == 0) {
				start = new Square(mapStr[m]);
				prev = start;
			}else {
				forkStart = new Square(mapStr[m]);
				prev = forkStart;
			}
			
			
			for(j = 1; j < mapLen[r]; m++, j++) {
				current = new Square(mapStr[m]);
				current.prev0 = prev;
				prev.next0 = current;
				prev = current;
				}
			
			if(fs == 0) {
				goal = current;
			}else {
				forkGoal = current;
			}
			
			if(fs != 0) {
				current = start;
				for(i = 0; i < fs; i++) {
					current = current.next0;
				}
				current.next1 = forkStart;
				forkStart.prev0 = current;
				for(i = 0; i < fg; i++) {
					current = current.next0;
				}
				current.prev1 = forkGoal;
				forkGoal.next0 = current;
			}
			
			r++;
			
		}while(m < mapStr.length);
	}
}
