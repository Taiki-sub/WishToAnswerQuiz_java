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

//�E���̃v���C���[�̃A�C�R���̉摜�̃N���X
public class E_QuizPlayerLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //�t�B�[���h
    Image e_QuizPlayerLabelImage;


    //�R���X�g���N�^
    E_QuizPlayerLabel(){
        //�摜�̐ݒ�
        e_QuizPlayerLabelImage= new ImageIcon(getClass().getClassLoader().getResource("E_QuizPlayer.jpg")).getImage();
        //�\��t����̈ʒu�ƃ��x���T�C�Y��ݒ�i�摜�ɍ��킹���T�C�Y�j
        this.setBounds(2400,600,400,400);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(e_QuizPlayerLabelImage,0,0,300,300,null);
    }
}