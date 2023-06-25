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

//�E���̃v���C���[�̃|�C���g��\������N���X
public class E_QuizPointLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //�t�B�[���h
    Image playerImage;
    Maze Maze;
    Quiz Quiz;

    //�R���X�g���N�^
    E_QuizPointLabel(){
        Maze = new Maze();
        Quiz = new Quiz();
        this.setText(String.valueOf(Quiz.e_point));
        this.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, Quiz.QuizPointLabe_fontsize));
        this.setForeground(Color.black);
        this.setBackground(Color.blue);
        this.setOpaque(true);
        this.setBounds(2350,900,400,400);
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(playerImage,0,0,Maze.WallSize,Maze.WallSize,null);
    }
}