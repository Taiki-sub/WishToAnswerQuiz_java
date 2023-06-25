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
import java.util.TimerTask;

import javax.swing.Timer;

import java.awt.Font;
import java.awt.Container;
import java.awt.BorderLayout;


public class TyosyoLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド
    int x;
    int y;
    int xVelocity;
    int yVelocity;
    Image image;
    ImageIcon imageicon;
    Timer timer = null;

    //コンストラクタ
    TyosyoLabel(){
        int fontsize = 50;
        //初期設定の座標と速さを決定
        x = new java.util.Random().nextInt(3000 -this.getWidth()- 500);
        y = new java.util.Random().nextInt(2000 -this.getHeight() - 500);
        xVelocity = new java.util.Random().nextInt(30);
        yVelocity = new java.util.Random().nextInt(30);
        this.setLocation(x,y);
        imageicon = new ImageIcon(getClass().getClassLoader().getResource("bakanisuru.jpg"));
        image= imageicon.getImage();
        //貼り付け先の位置とラベルサイズを設定（画像に合わせたサイズ）
        this.setBounds(x,y,imageicon.getIconWidth(),imageicon.getIconHeight());
        //this.setOpaque(true);
    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(image,0,0,imageicon.getIconWidth(),imageicon.getIconHeight(),null);
    }

}