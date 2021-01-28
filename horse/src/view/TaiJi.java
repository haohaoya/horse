package view;

import javax.swing.*;

public class TaiJi {
    private int state = 1;
    private boolean runOrNot = false;
    private JLabel TaiJiLable = null;

    public static ImageIcon[] taijiIcon = new ImageIcon[9];
    static {
        for(int i = 1;i<=8;i++){
            taijiIcon[i] = new ImageIcon("src/images/taiji"+i+".png");
        }
    }

    public void setTaiJiLable(JLabel lable){
        TaiJiLable = lable;
    }

    public void startRun(){
        this.runOrNot = true;
    }

    public void stopRun(){
        state = 1;
        this.runOrNot = false;
        this.TaiJiLable.setIcon(taijiIcon[1]);
    }

    public void runXuanzhuan(){
        if(this.runOrNot){
            state++;
            if(state == 9){
                state = 1;
            }
            this.TaiJiLable.setIcon(taijiIcon[state]);
        }
    }

}
