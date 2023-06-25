import java.util.Random;

public class Path{

    public void createPath(int[][] list){
        //�����̓��𐶐�����
        int cnt = 0;
        //�ł���܂Ń��[�v
        while(!tryCreatePath(Maze.list , Maze.Start_X_Index, Maze.Start_Y_Index , Maze.End_X_Index , Maze.End_Y_Index)){
            OuterWall w = new OuterWall();
            w.init(Maze.list);
            cnt++;

        }
        System.out.println("�񐔁F" + cnt + "\n");

        /*
        for(int i = 0; i < Maze.Masu; i++){
            for(int k = 0; k < Maze.Masu; k++){
                if(!list[i][k].isFixed()){
                    list[i][k] = 1;
                }
            }
        }

        for(int i = 0; i < Maze.Masu; i++){
            for(int k = 0; k < Maze.Masu; k++){
                if(list[i][k].isFixed){
                    list[i][k] = 1;
                }
            }
        }
        */


        //����̓��𐶐�����
        


    }


    private boolean tryCreatePath(int[][] list, int start_x_index, int start_y_index , int end_x_index, int end_y_index) {
        boolean flg[] = new boolean[4];//�㉺���E�i1�u���b�N�ɑ΂���A����L�΂��邩�ǂ����̃t���O�z��j
        RootCheaker r = new RootCheaker();

        //���݂̃u���b�N�̃C���f�b�N�X
        int current_x_index = start_x_index;
        int current_y_index = start_y_index;

        //1�u���b�N���ƂɃ��[�v
        while(true) {
            for(int i = 0 ; i < flg.length;i++){
                flg[i] = false;
            }
        //����L�΂��邩�ǂ����̐ݒ�
        //��
        if(current_y_index > 1){
            if(!(list[current_x_index][current_y_index-1] == 1)){
                flg[0] = r.canPath(current_x_index , current_y_index - 1,list);
            }
        }
        //��
        if(!(list[current_x_index][current_y_index + 1] == 1)){
            flg[1] = r.canPath(current_x_index , current_y_index + 1,list);
        }
        //��
        if(current_x_index > 1){
            if(!(list[current_x_index - 1][current_y_index] == 1)){
                flg[2] = r.canPath(current_x_index -1 , current_y_index,list);
            }
        }
        //�E
        if(!(list[current_x_index + 1][current_y_index] == 1)){
            flg[3] = r.canPath(current_x_index + 1 , current_y_index,list);
        }

        //�����_���ɓ���������ς���
        
        //����L�΂��Ȃ�
        //���g���C
        if(flg[0] == false && flg[1] == false && flg[2] == false && flg[3] == false){
            return false;
        }else {
            list[current_x_index][current_y_index] =1;
            Random rnd = new Random();
            int randomDirection = rnd.nextInt(flg.length);
            while (!flg[randomDirection]) {
                randomDirection = rnd.nextInt(flg.length);
            }
        }

        //����L�΂���
        }
    }
    
}