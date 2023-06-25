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


public class SelectIconLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //�t�B�[���h
    Image playerImage;
    public static Select cheakSelect;

    public enum Select{
        ANSWER01,
        ANSWER02,
        ANSWER03,
    }


    //�R���X�g���N�^
    SelectIconLabel(){
        cheakSelect = Select.ANSWER01;
        //�摜�̐ݒ�
        playerImage= new ImageIcon(getClass().getClassLoader().getResource("select.jpg")).getImage();
        //�\��t����̈ʒu�ƃ��x���T�C�Y��ݒ�i�摜�ɍ��킹���T�C�Y�j
        this.setBounds(200,1500,200,200);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(playerImage,0,0,200,200,null);
    }
}