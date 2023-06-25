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

import java.awt.Font;


import java.util.TimerTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;



public class WinnerPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    //�R���|�[�l���g
    JLabel title;
    JLabel message;
    JLabel text;
    //Border border = BorderFactory.createLineBorder(Color.BLACK,2);//����Ȃ���Ώ���
    MyKeyListener myKeyListener;
    GamePanel gamePanel;
    SyukuLabel syukulabel01;
    SyukuLabel syukulabel02;
    SyukuLabel syukulabel03;


    WinnerPanel(GamePanel receive){
        /*�p�l���T�C�Y�Ɠ\��t���ʒu�̐ݒ�͕s�v*/
        this.setLayout(null); //���C�A�E�g�̐ݒ�
        this.setBackground(Color.white); //�w�i�̐F
        //���̑��̒ǉ��@�\�͂����ɒǉ�   

        gamePanel = receive;   
    }

    public void prepareComponets(){
        
        syukulabel01 = new SyukuLabel();
        vitalizeCat(syukulabel01);

        syukulabel02 = new SyukuLabel();
        vitalizeCat(syukulabel02);

        syukulabel03 = new SyukuLabel();
        vitalizeCat(syukulabel03);
       

        message = new JLabel();
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setText("���Ȃ��͏������܂���");
        message.setFont(new Font("MV boil",Font.BOLD,150));
        message.setVerticalTextPosition(JLabel.CENTER);
        message.setHorizontalTextPosition(JLabel.CENTER);
        message.setBounds(500,700,2000,400);


        text = new JLabel();
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setText("space�L�[�ŏI�����Ă�");
        text.setFont(new Font("MV boil",Font.BOLD,50));
        text.setVerticalTextPosition(JLabel.CENTER);
        text.setHorizontalTextPosition(JLabel.CENTER);
        text.setBounds(1000,1700,1000,100);

        this.add(text);
        this.add(message);

        myKeyListener = new MyKeyListener(this);

    }

    private class MyKeyListener implements KeyListener{
        //�\��t����ێ�
        WinnerPanel panel;

        //�R���X�g���N�^
        MyKeyListener(WinnerPanel p){
            super();
            panel = p;
            p.addKeyListener(this);
        }
    
    
        @Override
        public void keyTyped(KeyEvent e) {//�}�E�X���I�u�W�F�N�g�ɓ������Ƃ��̏���
            //System.out.println("�}�E�X��������");
        }
        @Override
        public void keyReleased(KeyEvent e) {//�}�E�X���I�u�W�F�N�g����o���Ƃ��̏���
            //System.out.println("�}�E�X�E�o");
        }
        @Override
        public void keyPressed(KeyEvent e) {//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏����i�N���b�N�Ƃ̈Ⴂ�ɒ��Ӂj
            switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                     System.exit(0);
                break;
            }

        }
    }

    public void vitalizeCat(SyukuLabel c){
        CatActionListener catListener = new CatActionListener(c);
        c.timer = new Timer(10,catListener);
        this.add(c);
        c.timer.start();
    }

    private class CatActionListener implements ActionListener{

        private SyukuLabel cat;

        public CatActionListener(SyukuLabel c){
            cat = c;
        }

        @Override
        public void actionPerformed(ActionEvent c){
            if(cat.x > 3000 - ((Quiz.q_fontsize * Quiz.quiztextlist[0].length()) + 20) || cat.x < 0){
                cat.xVelocity = cat.xVelocity * (-1);
            }
            cat.x = cat.x + cat.xVelocity;
            if(cat.y > 2000  - (Quiz.q_fontsize* 3 + 20)|| cat.y < 0){
                cat.yVelocity = cat.yVelocity * (-1);
            }
            cat.y = cat.y + cat.yVelocity;
            cat.setLocation(cat.x,cat.y);
            cat.repaint();
            
        }

    }


}