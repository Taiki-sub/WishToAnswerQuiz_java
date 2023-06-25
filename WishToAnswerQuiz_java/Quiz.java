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

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
 

public class Quiz{
    //クイズに関する変数宣言
    public static SoundPlayer timerSoundPlayer;
    public static boolean DoAnswer = false;
    public final static int q_fontsize = 40;
    public final static int QuizPointLabe_fontsize = 300;
    public static int p_point = 0;
    public static int e_point = 0;
    public static boolean canAnswer ;
    public static String[] quiztextlist = {"オリンピックは何年ごと？",
                                        "33 + 11 - 2 = ?",
                                        "日本の初代内閣総理大臣は誰でしょうか？",
                                        "イギリスのお金の単位はどれでしょうか？",
                                        "日本で一番多い苗字は？",
                                        "桃太郎で一番最初のお供は？",
                                        "キリンの角は何本？",
                                        "60 - 2 + 20 = ?",
                                        "歳をとってしまうところはどこでしょう"};
    public static String[] choice01list = {"3年",
                                        "42",
                                        "大隈重信",
                                        "ドル" ,
                                        "鈴木",
                                        "キジ",
                                        "2本",
                                        "78",
                                        "廊下"};
    public static String[] choice02list = {"4年",
                                        "41",
                                        "小泉純一郎" ,
                                        "ユーロ",
                                        "佐藤",
                                        "猿",
                                        "3本",
                                        "79",
                                        "教室"};
    public static String[] choice03list = {"5年",
                                        "43",
                                        "伊藤博文",
                                        "ポンド",
                                        "田中",
                                        "犬",
                                        "5本",
                                        "80",
                                        "階段"};
    public static int[] answerindexlist = {1,0,2,2,1,2,2,0,0};
    public static int quizindex = 0;//何問目か
    public static int[] quizIndexlist = new int[]{0,1,2,3,4,5,6,7,8};//問題の順番のリスト

    public static void shuffle(int[] array) {
        // 配列が空か１要素ならシャッフルしようがないのので、そのままreturn
        if (array.length <= 1) {
            return;
        }

        // Fisher?Yates shuffle
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // 要素入れ替え(swap)
            int tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }

    public static void printArray(int[] array, String headerComment) {
        System.out.printf("%s -> %s\n\n", headerComment, Arrays.toString(array));
    }
}
