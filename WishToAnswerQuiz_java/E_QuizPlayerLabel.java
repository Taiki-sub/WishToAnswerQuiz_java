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

//右側のプレイヤーのアイコンの画像のクラス
public class E_QuizPlayerLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド
    Image e_QuizPlayerLabelImage;


    //コンストラクタ
    E_QuizPlayerLabel(){
        //画像の設定
        e_QuizPlayerLabelImage= new ImageIcon(getClass().getClassLoader().getResource("E_QuizPlayer.jpg")).getImage();
        //貼り付け先の位置とラベルサイズを設定（画像に合わせたサイズ）
        this.setBounds(2400,600,400,400);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(e_QuizPlayerLabelImage,0,0,300,300,null);
    }
}