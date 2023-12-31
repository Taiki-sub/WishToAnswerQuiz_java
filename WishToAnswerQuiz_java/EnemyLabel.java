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

//右側のプレイヤーを表示するクラス
public class EnemyLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド
    Image enemyImage;

    //コンストラクタ
    EnemyLabel(){
        
        //画像の設定
        enemyImage= new ImageIcon(getClass().getClassLoader().getResource("enemy.jpg")).getImage();
        //貼り付け先の位置とラベルサイズを設定（画像に合わせたサイズ）
        
        this.setBackground(Color.blue);
        this.setOpaque(true);
        //壁と同じサイズにすることで、はみ出ない
        this.setBounds(Maze.Start_enemy_X ,Maze.Start_enemy_Y, Maze.WallSize,Maze.WallSize);
        
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(enemyImage,0,0,Maze.WallSize,Maze.WallSize,null);
    }
}