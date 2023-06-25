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


public class TyosyoLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //�t�B�[���h
    int x;
    int y;
    int xVelocity;
    int yVelocity;
    Image image;
    ImageIcon imageicon;
    Timer timer = null;

    //�R���X�g���N�^
    TyosyoLabel(){
        int fontsize = 50;
        //�����ݒ�̍��W�Ƒ���������
        x = new java.util.Random().nextInt(3000 -this.getWidth()- 500);
        y = new java.util.Random().nextInt(2000 -this.getHeight() - 500);
        xVelocity = new java.util.Random().nextInt(30);
        yVelocity = new java.util.Random().nextInt(30);
        this.setLocation(x,y);
        imageicon = new ImageIcon(getClass().getClassLoader().getResource("bakanisuru.jpg"));
        image= imageicon.getImage();
        //�\��t����̈ʒu�ƃ��x���T�C�Y��ݒ�i�摜�ɍ��킹���T�C�Y�j
        this.setBounds(x,y,imageicon.getIconWidth(),imageicon.getIconHeight());
        //this.setOpaque(true);
    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(image,0,0,imageicon.getIconWidth(),imageicon.getIconHeight(),null);
    }

}