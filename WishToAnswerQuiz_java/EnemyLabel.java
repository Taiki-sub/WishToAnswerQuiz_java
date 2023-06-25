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

//�E���̃v���C���[��\������N���X
public class EnemyLabel extends JLabel{
    public static final long serialVersionUID = 1L;
    //�t�B�[���h
    Image enemyImage;

    //�R���X�g���N�^
    EnemyLabel(){
        
        //�摜�̐ݒ�
        enemyImage= new ImageIcon(getClass().getClassLoader().getResource("enemy.jpg")).getImage();
        //�\��t����̈ʒu�ƃ��x���T�C�Y��ݒ�i�摜�ɍ��킹���T�C�Y�j
        
        this.setBackground(Color.blue);
        this.setOpaque(true);
        //�ǂƓ����T�C�Y�ɂ��邱�ƂŁA�͂ݏo�Ȃ�
        this.setBounds(Maze.Start_enemy_X ,Maze.Start_enemy_Y, Maze.WallSize,Maze.WallSize);
        
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(enemyImage,0,0,Maze.WallSize,Maze.WallSize,null);
    }
}