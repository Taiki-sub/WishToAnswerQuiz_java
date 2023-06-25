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
        mainWindow = new MainWindow(); // ウィンドウのみの生成
        mainWindow.preparePanels(); //ペインに直接貼るパネルのみを生成
        mainWindow.prepareComponets(); //その他のコンポーネントを生成
        mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE); 
        // 起動後最初に表示すべき画面を手前に持ってきてそれに注目させる
        mainWindow.setVisible(true); //最後にウィンドウの可視化
    }
}