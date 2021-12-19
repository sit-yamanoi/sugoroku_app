package com.example.app;

public class Map {
	Square start = null;
	Square goal = null;
	int[][]  mapStr; //マップ構造 分岐スタート地点,合流地点,長さの順 0,1列目は全て0
	int[][] mapLen; //長い方のルートの長さ メイン,分岐,分岐の順
	
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
		
		int fs, fg; //分岐記憶
		Square current = null; //作成したマス
		Square prev = null; //前のマス
		Square forkStart = null; //分岐開始点
		Square forkGoal = null; //分岐終了点
		prev = forkStart;
		
		do {
			fs = mapStr[mapnum][m];
			m++;
			fg = mapStr[mapnum][m];
			m++;
			j = mapStr[mapnum][m];
			
			
			if(fs == 0) {
				start = new Square(mapStr[mapnum][m]);
				prev = start;
			}else {
				forkStart = new Square(mapStr[mapnum][m]);
			}
			
			
			for(j = 1; j < mapLen[mapnum][r]; m++, j++) {
				current = new Square(mapStr[mapnum][m]);
				current.prev0 = prev;
				prev.next0 = current;
				prev = current;
				}
			
			if(fs == 0) {
				goal = current;
			}else {
				forkStart = current;
			}
			
			if(fs != 0) {
				forkGoal = current;
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
		}while(m < mapStr[mapnum].length);
	}
}
