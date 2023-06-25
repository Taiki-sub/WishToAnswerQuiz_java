
import java.util.Random;
import java.util.Stack;
//���H����邽�߂̃N���X
public class CreateMaze {
    //�ϐ��錾
    static int mazeSize = Maze.Masu ;
    // ��: true, ��: false
    static boolean[][] wall;
    static int row;
    static int col;
    static Stack<Integer> rowStack = new Stack<Integer>();
    static Stack<Integer> colStack = new Stack<Integer>();
    static int usrRow = mazeSize - 1, usrCol = 1, goalRow = 0, goalCol = mazeSize - 2;

	//���͂��ꂽ���X�g���o�C�i���̖��H�̃��X�g�ɕϊ�����֐��i���@��@���̗p�j
    public static void createMaze(int[][] list) {
        wall = new boolean[mazeSize][mazeSize];
        // [�X�e�b�v1]������
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                wall[i][j] = true;
            }
        }

        // [�X�e�b�v2]�����_���ɊJ�n�ʒu��I�ԁi1 ? mazeSize - 2�j
        Random rnd = new Random();
        row = rnd.nextInt(mazeSize - 2) + 1;
        col = rnd.nextInt(mazeSize - 2) + 1;
        wall[row][col] = false;
        rowStack.push(row);
        colStack.push(col);

        boolean continueFlag = true;

        // [�X�e�b�v6]�ȉ��Awall[][]�S�̂𖄂߂�܂ŌJ��Ԃ�
        while (continueFlag) {

            // [�X�e�b�v3,4]�㉺���E�̂����ꂩ�Ɍ��E�܂œ���L�΂�
            extendPath();

            // [�X�e�b�v5]���ɂ��铹���玟�̊J�n�ʒu��I�ԁi0 ? mazeSize - 1�j
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


    // �����g�����郁�\�b�h
	static void extendPath() {
		boolean extendFlag = true;

		while (extendFlag) {
			extendFlag = extendPathSub();
		}
	}

	// ���̊g���ɐ���������true�A���s������false��Ԃ����\�b�h
	static boolean extendPathSub() {
		Random rmd = new Random();
		// ��: 0, ��: 1, ��: 2, �E: 3
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

	// �w�肵�������֊g���\�Ȃ��true�A�s�\�Ȃ��false��Ԃ����\�b�h
	static boolean canExtendPathWithDir(int direction) {
		int exRow = row, exCol = col;

		switch (direction) {
			case 0:	// ��
				exRow--;
				break;

			case 1:	// ��
				exRow++;
				break;

			case 2:	// ��
				exCol--;
				break;

			case 3:	// �E
				exCol++;
				break;
		}

		if (countSurroundingPath(exRow, exCol) > 1) {
			return false;
		}

		return true;
	}

	// ����1�}�X�ɂ��铹�̐��𐔂��郁�\�b�h
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

	// �w�肵��������1�}�Xrow��col���ړ������郁�\�b�h
	static void movePoint(int direction) {
		switch (direction) {
			case 0:	// ��
				row--;
				break;

			case 1:	// ��
				row++;
				break;

			case 2:	// ��
				col--;
				break;

			case 3:	// �E
				col++;
				break;
		}

		wall[row][col] = false;
		rowStack.push(row);
		colStack.push(col);
	}

	// �㉺���E�����ꂩ�̕����ֈړ��ł���Ȃ�true�A�ł��Ȃ��Ȃ�false��Ԃ����\�b�h
	static boolean canExtendPath() {
		return (canExtendPathWithDir(0) || canExtendPathWithDir(1) || canExtendPathWithDir(2) || canExtendPathWithDir(3));
	}

    // ���[�U�������ʒu�ɓ��������\�b�h
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

	// �S�[���������ʒu�ɓ��������\�b�h
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