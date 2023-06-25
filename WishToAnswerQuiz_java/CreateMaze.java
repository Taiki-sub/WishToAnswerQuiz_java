
import java.util.Random;
import java.util.Stack;
//迷路を作るためのクラス
public class CreateMaze {
    //変数宣言
    static int mazeSize = Maze.Masu ;
    // 壁: true, 道: false
    static boolean[][] wall;
    static int row;
    static int col;
    static Stack<Integer> rowStack = new Stack<Integer>();
    static Stack<Integer> colStack = new Stack<Integer>();
    static int usrRow = mazeSize - 1, usrCol = 1, goalRow = 0, goalCol = mazeSize - 2;

	//入力されたリストをバイナリの迷路のリストに変換する関数（穴掘り法を採用）
    public static void createMaze(int[][] list) {
        wall = new boolean[mazeSize][mazeSize];
        // [ステップ1]初期化
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                wall[i][j] = true;
            }
        }

        // [ステップ2]ランダムに開始位置を選ぶ（1 ? mazeSize - 2）
        Random rnd = new Random();
        row = rnd.nextInt(mazeSize - 2) + 1;
        col = rnd.nextInt(mazeSize - 2) + 1;
        wall[row][col] = false;
        rowStack.push(row);
        colStack.push(col);

        boolean continueFlag = true;

        // [ステップ6]以下、wall[][]全体を埋めるまで繰り返し
        while (continueFlag) {

            // [ステップ3,4]上下左右のいずれかに限界まで道を伸ばす
            extendPath();

            // [ステップ5]既にある道から次の開始位置を選ぶ（0 ? mazeSize - 1）
            continueFlag = false;

            while (!rowStack.empty() && !colStack.empty()) {
                row = rowStack.pop();
                col = colStack.pop();

                if (canExtendPath()) {
                    continueFlag = true;
                    break;
                }
            }
        }
        wall[usrRow][usrCol] = false;
        wall[usrRow][usrCol] = false;

        for(int i = 0 ; i < Maze.Masu; i++){
            for(int k = 0; k < Maze.Masu ;k++){
                if(wall[i][k]){
                    list[i][k] = 1;
                }else{
                    list[i][k]= 0;
                }

            }
        }
        
    }


    // 道を拡張するメソッド
	static void extendPath() {
		boolean extendFlag = true;

		while (extendFlag) {
			extendFlag = extendPathSub();
		}
	}

	// 道の拡張に成功したらtrue、失敗したらfalseを返すメソッド
	static boolean extendPathSub() {
		Random rmd = new Random();
		// 上: 0, 下: 1, 左: 2, 右: 3
		int direction = rmd.nextInt(4);

		for (int i = 0; i < 4; i++) {
			direction = (direction + i) % 4;
			if (canExtendPathWithDir(direction)) {
				movePoint(direction);
				return true;
			}
		}

		return false;
	}

	// 指定した方向へ拡張可能ならばtrue、不可能ならばfalseを返すメソッド
	static boolean canExtendPathWithDir(int direction) {
		int exRow = row, exCol = col;

		switch (direction) {
			case 0:	// 上
				exRow--;
				break;

			case 1:	// 下
				exRow++;
				break;

			case 2:	// 左
				exCol--;
				break;

			case 3:	// 右
				exCol++;
				break;
		}

		if (countSurroundingPath(exRow, exCol) > 1) {
			return false;
		}

		return true;
	}

	// 周囲1マスにある道の数を数えるメソッド
	static int countSurroundingPath(int row, int col) {
		int num = 0;

		if (row - 1 < 0 || !wall[row - 1][col]) {
			num++;
		}
		if (row + 1 > mazeSize - 1 || !wall[row + 1][col]) {
			num++;
		}
		if (col - 1 < 0 || !wall[row][col - 1]) {
			num++;
		}
		if (col + 1 > mazeSize - 1 || !wall[row][col + 1]) {
			num++;
		}

		return num;
	}

	// 指定した方向へ1マスrowとcolを移動させるメソッド
	static void movePoint(int direction) {
		switch (direction) {
			case 0:	// 上
				row--;
				break;

			case 1:	// 下
				row++;
				break;

			case 2:	// 左
				col--;
				break;

			case 3:	// 右
				col++;
				break;
		}

		wall[row][col] = false;
		rowStack.push(row);
		colStack.push(col);
	}

	// 上下左右いずれかの方向へ移動できるならtrue、できないならfalseを返すメソッド
	static boolean canExtendPath() {
		return (canExtendPathWithDir(0) || canExtendPathWithDir(1) || canExtendPathWithDir(2) || canExtendPathWithDir(3));
	}

    // ユーザを初期位置に動かすメソッド
	static void resetUsr() {
		usrRow = mazeSize - 1;
		usrCol = 1;

		while (true) {
			if (wall[usrRow - 1][usrCol]) {
				usrCol++;
			} else {
				break;
			}
		}

		wall[usrRow][usrCol] = false;
	}

	// ゴールを初期位置に動かすメソッド
	static void resetGoal() {
		goalRow = 0;
		goalCol = mazeSize - 2;

		while (true) {
			if (wall[goalRow + 1][goalCol]) {
				goalCol--;
			} else {
				break;
			}
		}

		wall[goalRow][goalCol] = false;
	}

    



}