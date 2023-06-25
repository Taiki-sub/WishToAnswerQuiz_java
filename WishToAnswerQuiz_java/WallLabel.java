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


public class WallLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド
    Image wallImage;

    //コンストラクタ
    WallLabel(){

        /*
        //画像の設定
        wallImage= new ImageIcon(getClass().getClassLoader().getResource("wall.jpg")).getImage();
        //貼り付け先の位置とラベルサイズを設定（画像に合わせたサイズ）
        */
        this.setBackground(Color.BLACK);
        this.setBounds(10,10,Maze.WallSize,Maze.WallSize);
        
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(wallImage,0,0,wallImage.getWidth(null),wallImage.getHeight(null),null);
    }
}