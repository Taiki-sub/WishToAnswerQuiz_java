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

public class Main{
    static MainWindow mainWindow;
    public static void main(String[] args){
        mainWindow = new MainWindow(); // �E�B���h�E�݂̂̐���
        mainWindow.preparePanels(); //�y�C���ɒ��ړ\��p�l���݂̂𐶐�
        mainWindow.prepareComponets(); //���̑��̃R���|�[�l���g�𐶐�
        mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE); 
        // �N����ŏ��ɕ\�����ׂ���ʂ���O�Ɏ����Ă��Ă���ɒ��ڂ�����
        mainWindow.setVisible(true); //�Ō�ɃE�B���h�E�̉���
    }
}