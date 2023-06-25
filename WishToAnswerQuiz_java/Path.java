import java.util.Random;

public class Path{

    public void createPath(int[][] list){
        //正解の道を生成する
        int cnt = 0;
        //できるまでループ
        while(!tryCreatePath(Maze.list , Maze.Start_X_Index, Maze.Start_Y_Index , Maze.End_X_Index , Maze.End_Y_Index)){
            OuterWall w = new OuterWall();
            w.init(Maze.list);
            cnt++;

        }
        System.out.println("回数：" + cnt + "\n");

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


        //分岐の道を生成する
        


    }


    private boolean tryCreatePath(int[][] list, int start_x_index, int start_y_index , int end_x_index, int end_y_index) {
        boolean flg[] = new boolean[4];//上下左右（1ブロックに対する、道を伸ばせるかどうかのフラグ配列）
        RootCheaker r = new RootCheaker();

        //現在のブロックのインデックス
        int current_x_index = start_x_index;
        int current_y_index = start_y_index;

        //1ブロックごとにループ
        while(true) {
            for(int i = 0 ; i < flg.length;i++){
                flg[i] = false;
            }
        //道を伸ばせるかどうかの設定
        //上
        if(current_y_index > 1){
            if(!(list[current_x_index][current_y_index-1] == 1)){
                flg[0] = r.canPath(current_x_index , current_y_index - 1,list);
            }
        }
        //下
        if(!(list[current_x_index][current_y_index + 1] == 1)){
            flg[1] = r.canPath(current_x_index , current_y_index + 1,list);
        }
        //左
        if(current_x_index > 1){
            if(!(list[current_x_index - 1][current_y_index] == 1)){
                flg[2] = r.canPath(current_x_index -1 , current_y_index,list);
            }
        }
        //右
        if(!(list[current_x_index + 1][current_y_index] == 1)){
            flg[3] = r.canPath(current_x_index + 1 , current_y_index,list);
        }

        //ランダムに道が向きを変える
        
        //道を伸ばせない
        //リトライ
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

        //道を伸ばせる
        }
    }
    
}