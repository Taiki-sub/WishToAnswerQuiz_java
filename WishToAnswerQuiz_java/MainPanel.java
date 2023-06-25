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


public class MainPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    //コンポーネント
    Color backgroundColor = Color.green;
    JLabel mainLabel;


    MainPanel(){
        /*パネルサイズと貼り付け位置の設定は不要*/
        this.setLayout(null); //レイアウトの設定
        this.setBackground(backgroundColor); //背景の色
        //その他の追加機能はここに追加      
    }

    public void prepareComponets(){
        //
        mainLabel = new JLabel();
        mainLabel.setText("メイン画面");
        mainLabel.setBounds(100,0,100,30);
        this.add(mainLabel);
    }


}