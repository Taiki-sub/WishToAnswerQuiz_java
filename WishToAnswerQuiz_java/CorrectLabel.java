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


public class CorrectLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //フィールド

    //コンストラクタ
    CorrectLabel(){
        int x = 950;
        int y = 600;
        String text = "正解";
        int fontsize = 500;
        this.setBounds(x,y,(fontsize * text.length()) + 200,fontsize + 200);
        this.setForeground(Color.red);
        this.setLocation(x,y);
        this.setText(text);
        this.setFont(new Font("ＭＳ ゴシック", Font.BOLD, fontsize));
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
    }

}