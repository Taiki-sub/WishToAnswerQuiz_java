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


public class ButtonLabel extends JLabel{
    //�V���A���C�Y�ȃN���X�̃o�[�W�������Ǘ����邽�߂ɒǉ�
    public static final long serialVersionUID = 1L;
    //�t�B�[���h
    Image buttonImage;

    //�R���X�g���N�^
    ButtonLabel(){
        //�摜�̐ݒ�
        buttonImage= new ImageIcon(getClass().getClassLoader().getResource("button.jpg")).getImage();
        //�\��t����̈ʒu�ƃ��x���T�C�Y��ݒ�
        this.setBounds(Maze.Button_X,Maze.Button_Y,Maze.WallSize,Maze.WallSize);
    }
    //�`��̂��߂̊֐�
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(buttonImage,0,0,Maze.WallSize,Maze.WallSize,null);
    }
}