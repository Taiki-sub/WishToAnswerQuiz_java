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


public class YouLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド
    int x;
    int y;
    int myNumber;
    Maze Maze;

    //コンストラクタ
    public YouLabel(){
        int myNumber = Maze.myNumber;
        System.out.println("YouLabel" + this.myNumber);
        String youtext = "YOU";
        int fontsize = 100;
        if(myNumber == 1){
            x = 280;
            y = 400;
        }
        else if(myNumber == 0){
            x = 2400;
            y = 400;
        }
        this.setBounds(x,y,(fontsize * youtext.length()) + 20,fontsize + 20);
        this.setLocation(x,y);
        this.setText(youtext);
        this.setFont(new Font("ＭＳ ゴシック", Font.BOLD, fontsize));
        this.setBackground(Color.green);
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);
    }

}