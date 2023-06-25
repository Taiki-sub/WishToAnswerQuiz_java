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
    //フィールド
    ScreenMode screenMode = ScreenMode.TITLE;
    //定数
    final int WIDTH = 3000;
    final int HEIGHT = 2000;
    //レイアウト（紙芝居のような設定用）
    CardLayout layout = new CardLayout();
    //コンポーネント
    TitlePanel titlePanel;
    GamePanel gamePanel;
    RulePanel rulePanel;
    WinnerPanel winnerPanel;
    LoserPanel loserPanel;

    //コンストラクタ
    MainWindow(){
        //ウィンドウ左上のアイコンとタイトル
        this.setTitle("タイトルを設定");
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("win.jpg"));
        this.setIconImage(icon.getImage());

        //いつもの
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);//画面サイズの変更禁止
        this.getContentPane().setBackground(Color.green);//背景の色を設定
        this.setLayout(layout);//紙芝居のように設定
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));//サイズ設定
        this.pack();//自動サイズ調整（これがないと変なサイズになる）
        this.setLocationRelativeTo(null);//起動時のスクリーンの位置を中心に（packより後で呼ぶ）
        // this.setLocation(450,450)  詳細に設定する場合
       
    }

    //コンストラクタが呼ばれた後のメインメソッドから最初に手動で呼び出す
    public void preparePanels(){
        //パネルの準備
        gamePanel = new GamePanel();
        this.add(gamePanel,"ゲーム画面");
        titlePanel = new TitlePanel(gamePanel);
        this.add(titlePanel,"タイトル画面");
        this.pack();
        rulePanel = new RulePanel(gamePanel);
        this.add(rulePanel,"ルール画面");
        winnerPanel = new WinnerPanel(gamePanel);
        this.add(winnerPanel,"勝利画面");
        loserPanel = new LoserPanel(gamePanel);
        this.add(loserPanel,"敗北画面");
        this.pack();

    }

    //preparePanels()が呼ばれたあと後、メインメソッドからさらに自動で呼び出す
    public void prepareComponets(){
        titlePanel.prepareComponets();
        gamePanel.prepareComponets();
        rulePanel.prepareComponets();
        winnerPanel.prepareComponets();
        loserPanel.prepareComponets();
    }

    //スクリーンモードを切り替える
    //メインメソッドから最後に手動で呼び出す
    public void setFrontScreenAndFocus(ScreenMode s){
        screenMode = s;
        //表示される画面の設定
        switch(screenMode){
            case TITLE:
                layout.show(this.getContentPane(),"タイトル画面");
                titlePanel.requestFocus();
                break;
            case GAME:
                layout.show(this.getContentPane(),"ゲーム画面");
                gamePanel.requestFocus();
                break;
            case RULE:
                layout.show(this.getContentPane(),"ルール画面");
                rulePanel.requestFocus();
                break;
            case WINNER:
                layout.show(this.getContentPane(),"勝利画面");
                winnerPanel.requestFocus();
                break;
            case LOSER:
                layout.show(this.getContentPane(),"敗北画面");
                loserPanel.requestFocus();
                break;
        }
    }
}
