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


public class RulePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    //�R���|�[�l���g
    JLabel title;
    JLabel message;
    //Border border = BorderFactory.createLineBorder(Color.BLACK,2);//����Ȃ���Ώ���
    MyKeyListener myKeyListener;
    GamePanel gamePanel;


    public enum Menu{
        START, 
        EXIT,
        RULE,
    }
    RulePanel(GamePanel receive){
        /*�p�l���T�C�Y�Ɠ\��t���ʒu�̐ݒ�͕s�v*/
        this.setLayout(null); //���C�A�E�g�̐ݒ�
        this.setBackground(Color.white); //�w�i�̐F
        //���̑��̒ǉ��@�\�͂����ɒǉ�   

        gamePanel = receive;   
    }

    public void prepareComponets(){
        
        

        ImageIcon icon = new ImageIcon("rule.jpg");
        title = new JLabel(icon);
        title.setBackground(Color.GRAY);
        title.setBounds(100,50,2800,1600);

        message = new JLabel();
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setText("SPACE�L�[�Ŗ߂�");
        message.setFont(new Font("MV boil",Font.BOLD,50));
        message.setVerticalTextPosition(JLabel.CENTER);
        message.setHorizontalTextPosition(JLabel.CENTER);
        message.setBounds(1300,1700,500,100);

        this.add(title);
        this.add(message);

        myKeyListener = new MyKeyListener(this);

    }

    private class MyKeyListener implements KeyListener{
        //�\��t����ێ�
        RulePanel panel;

        //�R���X�g���N�^
        MyKeyListener(RulePanel p){
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
                    //�J�n
                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
                break;
            }

        }
    }


}