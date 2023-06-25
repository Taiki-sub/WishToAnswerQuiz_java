import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
 
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.Timer;
import java.util.TimerTask;


public class RulePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    //コンポーネント
    JLabel title;
    JLabel message;
    //Border border = BorderFactory.createLineBorder(Color.BLACK,2);//いらなければ消す
    MyKeyListener myKeyListener;
    GamePanel gamePanel;


    public enum Menu{
        START, 
        EXIT,
        RULE,
    }
    RulePanel(GamePanel receive){
        /*パネルサイズと貼り付け位置の設定は不要*/
        this.setLayout(null); //レイアウトの設定
        this.setBackground(Color.white); //背景の色
        //その他の追加機能はここに追加   

        gamePanel = receive;   
    }

    public void prepareComponets(){
        
        

        ImageIcon icon = new ImageIcon("rule.jpg");
        title = new JLabel(icon);
        title.setBackground(Color.GRAY);
        title.setBounds(100,50,2800,1600);

        message = new JLabel();
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setText("SPACEキーで戻る");
        message.setFont(new Font("MV boil",Font.BOLD,50));
        message.setVerticalTextPosition(JLabel.CENTER);
        message.setHorizontalTextPosition(JLabel.CENTER);
        message.setBounds(1300,1700,500,100);

        this.add(title);
        this.add(message);

        myKeyListener = new MyKeyListener(this);

    }

    private class MyKeyListener implements KeyListener{
        //貼り付けを保持
        RulePanel panel;

        //コンストラクタ
        MyKeyListener(RulePanel p){
            super();
            panel = p;
            p.addKeyListener(this);
        }
    
    
        @Override
        public void keyTyped(KeyEvent e) {//マウスがオブジェクトに入ったときの処理
            //System.out.println("マウスが入った");
        }
        @Override
        public void keyReleased(KeyEvent e) {//マウスがオブジェクトから出たときの処理
            //System.out.println("マウス脱出");
        }
        @Override
        public void keyPressed(KeyEvent e) {//マウスでオブジェクトを押したときの処理（クリックとの違いに注意）
            switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                    //開始
                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
                break;
            }

        }
    }


}