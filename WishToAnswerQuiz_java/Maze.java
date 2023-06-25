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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 

public class Maze{
    //迷路に関する変数宣言
    public static int myNumber;
    public static int member;//多分使わない
    public static int canmove = 0;
    public final static int Maze_Size = 1200;
    public final static  int WallSize = 60;  //1マスのサイズ
    public final static  int Masu = Maze_Size / WallSize + 1;   //マスの個数
    public final static int Start_X_Index = 1;
    public final static int Start_Y_Index = 0;
    public final static int End_X_Index = Masu - 2;
    public final static int End_Y_Index = Masu - 1;
    public final static int Button_X_Index = 10;
    public final static int Button_Y_Index = 10;
    public final static int Button_X = Maze.Start_wall_X + (Maze.WallSize * Maze.Button_X_Index);
    public final static int Button_Y = Maze.Start_wall_Y + (Maze.WallSize * Maze.Button_Y_Index);
    public final static int Start_wall_X = 900;
    public final static int Start_wall_Y = 300;
    public final static int Start_player_X = 900 ;
    public final static int Start_player_Y = 1440;
    public final static int Start_enemy_X = 900 + (Masu - 1) * (WallSize);
    public final static int Start_enemy_Y = 360;

    
    public static int[][] list = new int[Masu][Masu]; //道と壁の場所を管理しているリスト（０が道、１が壁）
    public static List<Rectangle> WallList = new ArrayList<>(); //壁を格納しているリスト（データ型はRectangle）
    public static Rectangle box; //壁を生成するときに使う一旦代入するための変数
    public static WallLabel box02;

    public void printMyNumber(){
        System.out.println("Maze" + myNumber);
    }
}