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


public class MainWindow extends JFrame{
    public static final long serialVersionUID = 1L;
    //�t�B�[���h
    ScreenMode screenMode = ScreenMode.TITLE;
    //�萔
    final int WIDTH = 3000;
    final int HEIGHT = 2000;
    //���C�A�E�g�i���ŋ��̂悤�Ȑݒ�p�j
    CardLayout layout = new CardLayout();
    //�R���|�[�l���g
    TitlePanel titlePanel;
    GamePanel gamePanel;
    RulePanel rulePanel;
    WinnerPanel winnerPanel;
    LoserPanel loserPanel;

    //�R���X�g���N�^
    MainWindow(){
        //�E�B���h�E����̃A�C�R���ƃ^�C�g��
        this.setTitle("�^�C�g����ݒ�");
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("win.jpg"));
        this.setIconImage(icon.getImage());

        //������
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);//��ʃT�C�Y�̕ύX�֎~
        this.getContentPane().setBackground(Color.green);//�w�i�̐F��ݒ�
        this.setLayout(layout);//���ŋ��̂悤�ɐݒ�
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));//�T�C�Y�ݒ�
        this.pack();//�����T�C�Y�����i���ꂪ�Ȃ��ƕςȃT�C�Y�ɂȂ�j
        this.setLocationRelativeTo(null);//�N�����̃X�N���[���̈ʒu�𒆐S�Ɂipack����ŌĂԁj
        // this.setLocation(450,450)  �ڍׂɐݒ肷��ꍇ
       
    }

    //�R���X�g���N�^���Ă΂ꂽ��̃��C�����\�b�h����ŏ��Ɏ蓮�ŌĂяo��
    public void preparePanels(){
        //�p�l���̏���
        gamePanel = new GamePanel();
        this.add(gamePanel,"�Q�[�����");
        titlePanel = new TitlePanel(gamePanel);
        this.add(titlePanel,"�^�C�g�����");
        this.pack();
        rulePanel = new RulePanel(gamePanel);
        this.add(rulePanel,"���[�����");
        winnerPanel = new WinnerPanel(gamePanel);
        this.add(winnerPanel,"�������");
        loserPanel = new LoserPanel(gamePanel);
        this.add(loserPanel,"�s�k���");
        this.pack();

    }

    //preparePanels()���Ă΂ꂽ���ƌ�A���C�����\�b�h���炳��Ɏ����ŌĂяo��
    public void prepareComponets(){
        titlePanel.prepareComponets();
        gamePanel.prepareComponets();
        rulePanel.prepareComponets();
        winnerPanel.prepareComponets();
        loserPanel.prepareComponets();
    }

    //�X�N���[�����[�h��؂�ւ���
    //���C�����\�b�h����Ō�Ɏ蓮�ŌĂяo��
    public void setFrontScreenAndFocus(ScreenMode s){
        screenMode = s;
        //�\��������ʂ̐ݒ�
        switch(screenMode){
            case TITLE:
                layout.show(this.getContentPane(),"�^�C�g�����");
                titlePanel.requestFocus();
                break;
            case GAME:
                layout.show(this.getContentPane(),"�Q�[�����");
                gamePanel.requestFocus();
                break;
            case RULE:
                layout.show(this.getContentPane(),"���[�����");
                rulePanel.requestFocus();
                break;
            case WINNER:
                layout.show(this.getContentPane(),"�������");
                winnerPanel.requestFocus();
                break;
            case LOSER:
                layout.show(this.getContentPane(),"�s�k���");
                loserPanel.requestFocus();
                break;
        }
    }
}
