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


public class MainPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    //�R���|�[�l���g
    Color backgroundColor = Color.green;
    JLabel mainLabel;


    MainPanel(){
        /*�p�l���T�C�Y�Ɠ\��t���ʒu�̐ݒ�͕s�v*/
        this.setLayout(null); //���C�A�E�g�̐ݒ�
        this.setBackground(backgroundColor); //�w�i�̐F
        //���̑��̒ǉ��@�\�͂����ɒǉ�      
    }

    public void prepareComponets(){
        //
        mainLabel = new JLabel();
        mainLabel.setText("���C�����");
        mainLabel.setBounds(100,0,100,30);
        this.add(mainLabel);
    }


}