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

//�I�����R�Ɋւ���N���X
public class Choice03Label extends JLabel{
    public static final long serialVersionUID = 1L;
    //�t�B�[���h
    int x;
    int y;

    //�R���X�g���N�^
    public Choice03Label(){
        ////Quiz���烊�X�g�Ɩ��ԍ����擾
        String answertext = Quiz.choice03list[Quiz.quizIndexlist[Quiz.quizindex]];
        int fontsize = 100;
        this.setBounds(1900,1700,(fontsize * answertext.length()) + 200,fontsize + 20);
        this.setText("3:" + answertext);
        this.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, fontsize));
        this.setBackground(Color.yellow);
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);
    }
    //���X�V�̂��߂̃N���X
    public void setchoice03(){
        int fontsize = 100;
        String answertext = Quiz.choice03list[Quiz.quizIndexlist[Quiz.quizindex]];
        this.setBounds(1900,1700,(fontsize * answertext.length()) + 200,fontsize + 20);
        this.setText("3:" + answertext); 
    }

}