package house;

public class Horse {
    static private final int[] dx = new int[]{-2,-1,1,2,-2,-1,1,2};
    static private final int[] dy = new int[]{-1,-2,-2,-1,1,2,2,1};
    private int[][] chessboard = new int[8][8];
    private boolean flag = false;
    private int[] xWalkMethods = new int[65];
    private int[] yWalkMethods = new int[65];
    public Horse(int x,int y){
        for(int i = 0;i<8;i++){
            for(int j = 0;j<8;j++){
                chessboard[i][j] = 0;
            }
        }
        chessboard[x][y] = 1;
        Move(x,y,2);
    }

    private int wayOut(int x,int y){
        int count = 0;
        int tx,ty,i;
        if(x<0||x>7||y<0||y>7||chessboard[x][y]!=0){
            return 9;
        }
        for(i = 0;i<8;i++){
            tx = x+dx[i];
            ty = y+dy[i];
            if(tx>-1 && tx<8 && ty>-1 && ty<8 && chessboard[tx][ty]==0){
                count++;
            }
        }
        return count;
    }

    private void sort(Direction[] next){
        int i,j,index;
        Direction temp = null;
        for(i=0;i<8;i++){
            index = i;
            for(j=i+1;j<8;j++){
                if(next[index].wayOutNum>next[j].wayOutNum){
                    index = j;
                }
            }
            if(i!=index){
                temp = next[i];
                next[i] = next[index];
                next[index] = temp;
            }
        }
        for(i=0;i<8;i++){
            System.out.println(next[i].wayOutNum+"  "+i);
        }
    }

    private void Move(int x,int y,int step){
        int i,j;
        int tx,ty;
        if(flag){
            return;
        }
        if(step == 64){
            this.flag = true;
            for(i=0;i<8;i++){
                for(j=0;j<8;j++){
                    xWalkMethods[chessboard[i][j]] = i;
                    yWalkMethods[chessboard[i][j]] = j;
                }
            }
            xWalkMethods[64] = xWalkMethods[0];
            yWalkMethods[64] = yWalkMethods[0];
        }

        Direction[] next = new Direction[8];

        for(i=0;i<8;i++){
            Direction temp = new Direction();
            temp.x = x+dx[i];
            temp.y = y+dy[i];
            next[i] = temp;
            next[i].wayOutNum = wayOut(temp.x,temp.y);
        }

        sort(next);

        for(i = 0;i<8;i++){
            tx = next[i].x;
            ty = next[i].y;
            chessboard[tx][ty] = step;
            Move(tx,ty,step+1);
            if(flag){
                return;
            }
            chessboard[tx][ty] = 0;
        }

    }

    public int[] getxWalkMethods() {
        return xWalkMethods;
    }

    public int[] getyWalkMethods() {
        return yWalkMethods;
    }
}

