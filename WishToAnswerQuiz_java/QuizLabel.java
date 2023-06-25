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


public class QuizLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド
    int x;
    int y;
    int xVelocity;
    int yVelocity;
    Timer timer = null;

    //コンストラクタ
    QuizLabel(){
        int fontsize = 50;
        //初期設定の座標と速さを決定
        x = new java.util.Random().nextInt(3000 -this.getWidth()- 500);
        y = new java.util.Random().nextInt(2000 -this.getHeight() - 500);
        this.setBounds(x,y,(Quiz.q_fontsize * Quiz.quiztextlist[Quiz.quizindex].length()) + 400,Quiz.q_fontsize + 20);
        xVelocity = -5 + new java.util.Random().nextInt(25);
        yVelocity = -5 + new java.util.Random().nextInt(25);
        this.setLocation(x,y);
        this.setText(Quiz.quiztextlist[Quiz.quizIndexlist[Quiz.quizindex]]);
        this.setFont(new Font("ＭＳ ゴシック", Font.BOLD, Quiz.q_fontsize));
        this.setBackground(Color.green);
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
    }
    public void setQuizText(){
        this.setText(Quiz.quiztextlist[Quiz.quizIndexlist[Quiz.quizindex]]); 
    }

}