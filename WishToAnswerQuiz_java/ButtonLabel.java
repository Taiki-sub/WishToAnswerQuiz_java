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


public class ButtonLabel extends JLabel{
    //シリアライズなクラスのバージョンを管理するために追加
    public static final long serialVersionUID = 1L;
    //フィールド
    Image buttonImage;

    //コンストラクタ
    ButtonLabel(){
        //画像の設定
        buttonImage= new ImageIcon(getClass().getClassLoader().getResource("button.jpg")).getImage();
        //貼り付け先の位置とラベルサイズを設定
        this.setBounds(Maze.Button_X,Maze.Button_Y,Maze.WallSize,Maze.WallSize);
    }
    //描画のための関数
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(buttonImage,0,0,Maze.WallSize,Maze.WallSize,null);
    }
}