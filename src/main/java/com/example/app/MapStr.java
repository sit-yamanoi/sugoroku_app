package com.example.app;

public class MapStr {
	//マップ構造 [分岐スタート地点][合流地点][コマの効果][...]の順 0,1列目は全て0
	//ルートの長さ [メインの長さ][分岐の長さ][分岐の長さ]の順
	//0:何もなし 1:getItem 2:goFoward 3:backFoward 4:crewRestart 5:crewTakeItem 6:crewGiveItem 7:振り出し
<<<<<<< HEAD
	static int[] map =	{0, 0,	
						 0, 0, 0, 0, 1, 0, 0, 0, 0, 1,
						 0, 0, 0, 0, 0, 2, 0, 1, 2, 0,
						 3, 0, 1, 0, 3, 0, 0, 5, 0, 0,
						 0, 0, 6, 0, 0, 0, 0, 6, 0, 0,
						 0, 0, 0, 5, 2, 0, 3, 1, 2, 0,
						 0, 0, 2, 0, 3, 1, 0, 1, 0, 6,
						 2, 0, 0, 2, 0, 3, 0, 0, 0,
						 13,26,
						 1, 1, 1, 1, 7, 7, 7, 1, 1, 6,
						 1, 1,
						 28,41,
						 3, 3, 2, 2, 3, 3, 2, 3, 2, 2,
						 3, 3
						};
	
	static int[] len = {67,12,12};
=======
	static int[] str =	{0, 0,	
						 0, 0, 1, 1, 2, 2, 0, 0, 0, 1,
						 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
						 2, 2, 2, 3, 0, 0, 0, 0, 0, 0,
						 15,20,
						 4, 1, 4, 2};
	
	static int[] len = {30, 4};
>>>>>>> branch 'testMap' of https://github.com/sit-yamanoi/sugoroku_app.git
}
