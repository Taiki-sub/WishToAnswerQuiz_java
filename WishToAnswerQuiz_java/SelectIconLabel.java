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


public class SelectIconLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド
    Image playerImage;
    public static Select cheakSelect;

    public enum Select{
        ANSWER01,
        ANSWER02,
        ANSWER03,
    }


    //コンストラクタ
    SelectIconLabel(){
        cheakSelect = Select.ANSWER01;
        //画像の設定
        playerImage= new ImageIcon(getClass().getClassLoader().getResource("select.jpg")).getImage();
        //貼り付け先の位置とラベルサイズを設定（画像に合わせたサイズ）
        this.setBounds(200,1500,200,200);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(playerImage,0,0,200,200,null);
    }
}