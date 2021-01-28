package view;

import house.Horse;
import sun.audio.AudioPlayer;
import util.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

public class MainFrame extends BaseFrame {
    //主面板
    private JPanel mainPanel = new JPanel();
    private JPanel qipanPanel = new JPanel();

    private ImageIcon backgroundImageIcon = new ImageIcon("src/images/bg.jpg");
    private JLabel background = new JLabel(backgroundImageIcon);

    private ImageIcon qipanImageIcon = new ImageIcon("src/images/qipan.png");
    private JLabel qipan = new JLabel(qipanImageIcon);

    private ImageIcon qigeImageIcon = new ImageIcon("src/images/qige.png");
    private JLabel qige = new JLabel(qigeImageIcon);

    private JLabel[][] taijiLables = new JLabel[9][9];
    private TaiJi[][] taijis = new TaiJi[9][9];

    private JLabel poxtextLable = new JLabel("起始坐标:");
    private JLabel pox = new JLabel("(1,1)");

    private taijiXuanzhuanThread thread = new taijiXuanzhuanThread();
    private horseWalkThread thread2;
    private soundThread thread3;

    private boolean stopOrRunThread2 = false;
    private boolean stopOrRunThread3 = false;

    private JButton zidingyiPoxButton = new JButton("更改坐标");
    private JButton suijiPoxButton = new JButton("随机坐标");
    private JButton startButton = new JButton("开始");
    private JButton stopButton = new JButton("停止");

    private JPanel getinPanel = new JPanel();
    private JLabel xLabel = new JLabel("X:");
    private JTextArea xPos = new JTextArea();
    private JLabel yLabel = new JLabel("Y:");
    private JTextArea yPos = new JTextArea();
    private JButton insureButton = new JButton("确认");

    private ImageIcon horseimage = new ImageIcon("src/images/horse.png");
    private JLabel horseLable = new JLabel(horseimage);

    private Integer intx = 1;
    private Integer inty = 1;
    private int[] xMethods;
    private int[] yMethods;

    private MainFrame(){}
    public MainFrame(String title){
        super(title);
        for(int i = 1;i<=8;i++){
            for(int j = 1;j<=8;j++){
                taijiLables[i][j] = new JLabel(TaiJi.taijiIcon[1]);
                taijis[i][j] = new TaiJi();
                taijis[i][j].setTaiJiLable(taijiLables[i][j]);
            }
        }
        this.init();
    }

    protected void setFontAndSoOn() {
        //设置布局管理方式
        mainPanel.setLayout(null);
        qipanPanel.setLayout(null);
        getinPanel.setLayout(null);
        //设置棋盘panel透明
        qipanPanel.setOpaque(false);
        //设置背景图片位置大小
        background.setBounds(0,0,750,1024);
        qipan.setBounds(119,256,512,512);
        qige.setBounds(115,252,520,520);

        poxtextLable.setBounds(10,10,300,80);
        poxtextLable.setFont(new Font("楷体",Font.PLAIN,45));
        poxtextLable.setForeground(Color.gray);
        pox.setBounds(150,90,200,85);
        pox.setFont(new Font("楷体",Font.PLAIN,80));
        pox.setForeground(Color.black);

        //放置太极lables里的64个太极
        for(int i = 1; i<=8; i++){
            for(int j = 1;j<=8;j++){
                taijiLables[i][j].setBounds(8*j+56*(j-1),8*i+56*(i-1),56,56);
            }
        }

        horseLable.setBounds(-100,-100,56,56);

        qipanPanel.setBounds(115,252,512,512);

        zidingyiPoxButton.setBounds(20,90,120,32);
        zidingyiPoxButton.setFocusPainted(false);
        zidingyiPoxButton.setFont(new Font("楷体",Font.PLAIN,18));

        suijiPoxButton.setBounds(20,140,120,32);
        suijiPoxButton.setFocusPainted(false);
        suijiPoxButton.setFont(new Font("楷体",Font.PLAIN,18));

        startButton.setBounds(50,185,150,52);
        startButton.setFocusPainted(false);
        startButton.setFont(new Font("楷体",Font.PLAIN,24));


        stopButton.setBounds(50,185,150,52);
        stopButton.setFocusPainted(false);
        stopButton.setFont(new Font("楷体",Font.PLAIN,24));
        stopButton.setFocusPainted(false);
        stopButton.setVisible(false);


        getinPanel.setBounds(165,355,420,260);
        getinPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,5));
        xLabel.setBounds(50,5,90,90);
        xLabel.setFont(new Font("楷体",Font.BOLD,80));
        yLabel.setBounds(50,95,90,90);
        yLabel.setFont(new Font("楷体",Font.BOLD,80));
        xPos.setBounds(150,15,180,70);
        xPos.setFont(new Font("楷体",Font.BOLD,60));
        yPos.setBounds(150,105,180,70);
        yPos.setFont(new Font("楷体",Font.BOLD,60));

        insureButton.setBounds(145,190,120,45);
        insureButton.setFocusPainted(false);
        insureButton.setFont(new Font("楷体",Font.PLAIN,24));
        getinPanel.setVisible(false);
    }

    protected void addElement() {
        getinPanel.add(xLabel);
        getinPanel.add(xPos);
        getinPanel.add(yLabel);
        getinPanel.add(yPos);
        getinPanel.add(insureButton);
        mainPanel.add(getinPanel);
        qipanPanel.add(horseLable);
        for(int i = 1;i<=8;i++){
            for(int j = 1;j<=8;j++){
                qipanPanel.add(taijiLables[i][j]);
            }
        }
        mainPanel.add(zidingyiPoxButton);
        mainPanel.add(suijiPoxButton);
        mainPanel.add(stopButton);
        mainPanel.add(startButton);
        mainPanel.add(poxtextLable);
        mainPanel.add(pox);
        mainPanel.add(qipanPanel);
        mainPanel.add(qige);
        mainPanel.add(qipan);
        mainPanel.add(background);
        this.add(mainPanel);

    }

    protected void addListener() {
        zidingyiPoxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getinPanel.setVisible(true);
                suijiPoxButton.setEnabled(false);
                startButton.setEnabled(false);
                zidingyiPoxButton.setEnabled(false);
            }
        });

        insureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = Integer.parseInt(xPos.getText());
                int y = Integer.parseInt(yPos.getText());
                if(x>=1&&x<=8&&y>=1&&y<=8){
                    intx = x;
                    inty = y;
                    pox.setText("("+x+","+y+")");
                    getinPanel.setVisible(false);
                    xPos.setText("");
                    yPos.setText("");
                    suijiPoxButton.setEnabled(true);
                    startButton.setEnabled(true);
                    zidingyiPoxButton.setEnabled(true);
                }else{
                    xPos.setText("");
                    yPos.setText("");
                    JOptionPane.showMessageDialog(MainFrame.this,"输入有误，请重新输入");
                }
            }
        });

        suijiPoxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Random random = new Random(System.currentTimeMillis());
                intx = random.nextInt(8)+1;
                inty = random.nextInt(8)+1;
                pox.setText("("+intx+","+inty+")");
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suijiPoxButton.setEnabled(false);
                zidingyiPoxButton.setEnabled(false);
                Horse horse = new Horse(intx-1,inty-1);
                xMethods = horse.getxWalkMethods();
                yMethods = horse.getyWalkMethods();
                //这里的坐标是从0 开始的
                thread2 = new horseWalkThread();
                thread2.start();
                stopButton.setVisible(true);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopButton.setVisible(false);
                horseLable.setBounds(-100,-100,56,56);
                stopOrRunThread2 = true;
                stopOrRunThread3 = true;
                for(int i = 1;i<=8;i++){
                    for(int j = 1;j<=8;j++){
                        taijis[i][j].stopRun();
                    }
                }

                suijiPoxButton.setEnabled(true);
                zidingyiPoxButton.setEnabled(true);

            }
        });
    }

    protected void setFrameSelf() {
        //设置窗口位置和大小
        this.setBounds(500,0,750,1024);
        //设置点击关闭退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体大小不可拖拽
        this.setResizable(false);
        //设置窗体显示状态
        this.setVisible(true);

        thread.start();
    }

    private class taijiXuanzhuanThread extends Thread{
        public void run(){
            while(true) {
                for (int i = 1; i <= 8; i++) {
                    for (int j = 1; j <= 8; j++) {
                        taijis[i][j].runXuanzhuan();
                    }
                }
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class horseWalkThread extends Thread{
        public void run(){
            thread3 = new soundThread();
            thread3.start();
            for(int i = 1;i<=64;i++){
                if(stopOrRunThread2) {
                    stopOrRunThread2 = false;
                    for(int m = 1;m<=8;m++){
                        for(int n = 1;n<=8;n++){
                            taijis[m][n].stopRun();
                        }
                    }
                    break;
                }
                if(i!=1) {
                    for (int j = 1; j <= 20; j++) {
                        if(stopOrRunThread2){
                            break;
                        }
                        int tx = (8 * (xMethods[i] + 1-xMethods[i-1]-1) + 56 * (xMethods[i]-xMethods[i-1]))*j/20+((xMethods[i-1]+1)*8+xMethods[i-1]*56);
                        int ty = (8 * (yMethods[i] + 1-yMethods[i-1]-1) + 56 * (yMethods[i]-yMethods[i-1]))*j/20+((yMethods[i-1]+1)*8+yMethods[i-1]*56);
                        horseLable.setBounds(tx, ty, 56, 56);
                        try {
                            Thread.sleep(50);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                }else{
                    horseLable.setBounds(8 * (xMethods[i] + 1) + 56 * xMethods[i] , 8 * (yMethods[i] + 1) + 56 * yMethods[i], 56, 56);
                }
                taijis[yMethods[i]+1][xMethods[i]+1].startRun();
                if(stopOrRunThread2){
                    stopOrRunThread2 = false;
                    for(int m = 1;m<=8;m++){
                        for(int n = 1;n<=8;n++){
                            taijis[m][n].stopRun();
                        }
                    }
                    break;
                }
                try {
                    Thread.sleep(800);
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }

            stopOrRunThread3 = true;

        }

    }

    private class soundThread extends Thread{
        public void run(){
            int i = 0;
            while(!stopOrRunThread3){
                if (i % 2 == 0) {
                    try {
                        InputStream inputStream = new FileInputStream(new File("src/sounds/pao.wav"));
                        AudioPlayer.player.start(inputStream);
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                if(i % 10 ==0) {
                    try {
                        InputStream inputStream = new FileInputStream(new File("src/sounds/jiao.wav"));
                        AudioPlayer.player.start(inputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                i++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopOrRunThread3 = false;
        }
    }

}
