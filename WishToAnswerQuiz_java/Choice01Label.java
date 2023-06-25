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

//選択肢１に関するクラス
public class Choice01Label extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド
    int x;
    int y;
    Maze Maze;

    //コンストラクタ
    public Choice01Label(){
        //Quizからリストと問題番号を取得
        String answertext = Quiz.choice01list[Quiz.quizIndexlist[Quiz.quizindex]];
        int fontsize = 100;
        this.setBounds(100,1700,(fontsize * answertext.length()) + 200,fontsize + 20);
        this.setText("1:" + answertext);
        this.setFont(new Font("ＭＳ ゴシック", Font.BOLD, fontsize));
        this.setBackground(Color.yellow);
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);
    }
    //問題更新のクラス
    public void setchoice01(){
        int fontsize = 100;
        String answertext = Quiz.choice01list[Quiz.quizIndexlist[Quiz.quizindex]];
        this.setBounds(100,1700,(fontsize * answertext.length()) + 200,fontsize + 20);
        this.setText("1:" + answertext); 
    }

}