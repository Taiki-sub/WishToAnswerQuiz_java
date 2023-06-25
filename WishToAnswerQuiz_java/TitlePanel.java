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


public class TitlePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    //�R���|�[�l���g
    JLabel title;
    JLabel start;
    JLabel exit;
    JLabel rule;
    JLabel select;
    JLabel message;
    Menu cheakMenu = Menu.START;
    //Border border = BorderFactory.createLineBorder(Color.BLACK,2);//����Ȃ���Ώ���
    MyKeyListener myKeyListener;
    GamePanel gamePanel;


    public enum Menu{
        START, 
        EXIT,
        RULE,
    }
    TitlePanel(GamePanel receive){
        /*�p�l���T�C�Y�Ɠ\��t���ʒu�̐ݒ�͕s�v*/
        this.setLayout(null); //���C�A�E�g�̐ݒ�
        this.setBackground(Color.white); //�w�i�̐F
        //���̑��̒ǉ��@�\�͂����ɒǉ�   

        gamePanel = receive;   
    }

    public void prepareComponets(){
        
        
        /*
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setText("Created by �yYOUR NAME�z");
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(Color.GRAY);
        title.setBounds(100,100,2800,1200);
        */
        ImageIcon icon = new ImageIcon("titlepanle_copy.jpg");
        title = new JLabel(icon);
        title.setBackground(Color.GRAY);
        title.setBounds(100,100,2800,1200);
        //title.setBorder(border);

        //�I����
        start = new JLabel();
        start.setText("STRAT");
        start.setFont(new Font("MV boil",Font.BOLD,100));
        start.setHorizontalTextPosition(JLabel.CENTER);
        start.setVerticalTextPosition(JLabel.BOTTOM);
        start.setOpaque(true);
        start.setBackground(Color.orange);
        start.setBounds(1300,1450,400,100);
        //start.setBorder(border);

        exit = new JLabel();
        exit.setText("EXIT");
        exit.setFont(new Font("MV boil",Font.BOLD,100));
        exit.setHorizontalTextPosition(JLabel.CENTER);
        exit.setVerticalTextPosition(JLabel.BOTTOM);
        exit.setOpaque(true);
        exit.setBackground(Color.orange);
        exit.setBounds(1300,1750,400,100);
        //exit.setBorder(border);

        rule = new JLabel();
        rule.setText("RULE");
        rule.setFont(new Font("MV boil",Font.BOLD,100));
        rule.setHorizontalTextPosition(JLabel.CENTER);
        rule.setVerticalTextPosition(JLabel.BOTTOM);
        rule.setOpaque(true);
        rule.setBackground(Color.orange);
        rule.setBounds(1300,1600,400,100);

        select = new JLabel();
        select.setBackground(Color.blue);
        select.setOpaque(true);
        select.setBounds(1100,1475,50,50);
        //select.setBorder(border);

        message = new JLabel();
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setText("�I���F��,��  ����:SPACE");
        message.setFont(new Font("MV boil",Font.BOLD,30));
        message.setVerticalTextPosition(JLabel.CENTER);
        message.setHorizontalTextPosition(JLabel.CENTER);
        message.setBounds(1300,1300,400,100);
        //message.setBorder(border);

        this.setLayout(null);
        this.add(title);
        this.add(start);
        this.add(exit);
        this.add(rule);
        this.add(select);
        this.add(message);

        myKeyListener = new MyKeyListener(this);

    }

    private class MyKeyListener implements KeyListener{
        //�\��t����ێ�
        TitlePanel panel;

        //�R���X�g���N�^
        MyKeyListener(TitlePanel p){
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
            case KeyEvent.VK_DOWN:
                if(cheakMenu == Menu.START){
                    select.setLocation(select.getX(),select.getY()+150);
                    cheakMenu = Menu.RULE;
                }
                else if(cheakMenu == Menu.RULE){
                    select.setLocation(select.getX(),select.getY()+150);
                    cheakMenu = Menu.EXIT;
                }
                break;
            case KeyEvent.VK_UP:
                if(cheakMenu == Menu.EXIT){
                    select.setLocation(select.getX(),select.getY()-150);
                    cheakMenu = Menu.RULE;
                } else if(cheakMenu == Menu.RULE){
                    select.setLocation(select.getX(),select.getY()-150);
                    cheakMenu = Menu.START;
                }
                break;
            case KeyEvent.VK_SPACE:
                if(cheakMenu == Menu.START){
                    //�J�n
                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.GAME);
                    gamePanel.signalStart();
                    
                    Quiz.timerSoundPlayer = new SoundPlayer("timer.wav");
                    Quiz.timerSoundPlayer.SetLoop(true);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                    Quiz.timerSoundPlayer.play();
                    
                }else if(cheakMenu == Menu.EXIT){
                    System.exit(0);
                }else if(cheakMenu == Menu.RULE){
                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.RULE);
                }
                break;

            }

        }
    }


}