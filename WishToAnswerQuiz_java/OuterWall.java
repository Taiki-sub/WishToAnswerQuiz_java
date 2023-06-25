public class OuterWall {

    public void init(int[][] list) {
        int count = 0;
        for(int i = 0 ; i < Maze.Masu; i++){
            for(int k = 0; k < Maze.Masu ;k++){
                if(i == 0 || k == 0 || i == Maze.Masu -1 || k == Maze.Masu - 1){
                    list[i][k] = 1;
                }else{
                    list[i][k]= 0;
                }

            }
        }
        list[Maze.Start_X_Index][Maze.Start_Y_Index] = 0;
        list[Maze.End_X_Index][Maze.End_Y_Index] = 0;
    }
}