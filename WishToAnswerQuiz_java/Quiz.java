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
    //�N�C�Y�Ɋւ���ϐ��錾
    public static SoundPlayer timerSoundPlayer;
    public static boolean DoAnswer = false;
    public final static int q_fontsize = 40;
    public final static int QuizPointLabe_fontsize = 300;
    public static int p_point = 0;
    public static int e_point = 0;
    public static boolean canAnswer ;
    public static String[] quiztextlist = {"�I�����s�b�N�͉��N���ƁH",
                                        "33 + 11 - 2 = ?",
                                        "���{�̏�����t������b�͒N�ł��傤���H",
                                        "�C�M���X�̂����̒P�ʂ͂ǂ�ł��傤���H",
                                        "���{�ň�ԑ����c���́H",
                                        "�����Y�ň�ԍŏ��̂����́H",
                                        "�L�����̊p�͉��{�H",
                                        "60 - 2 + 20 = ?",
                                        "�΂��Ƃ��Ă��܂��Ƃ���͂ǂ��ł��傤"};
    public static String[] choice01list = {"3�N",
                                        "42",
                                        "��G�d�M",
                                        "�h��" ,
                                        "���",
                                        "�L�W",
                                        "2�{",
                                        "78",
                                        "�L��"};
    public static String[] choice02list = {"4�N",
                                        "41",
                                        "���򏃈�Y" ,
                                        "���[��",
                                        "����",
                                        "��",
                                        "3�{",
                                        "79",
                                        "����"};
    public static String[] choice03list = {"5�N",
                                        "43",
                                        "�ɓ�����",
                                        "�|���h",
                                        "�c��",
                                        "��",
                                        "5�{",
                                        "80",
                                        "�K�i"};
    public static int[] answerindexlist = {1,0,2,2,1,2,2,0,0};
    public static int quizindex = 0;//����ڂ�
    public static int[] quizIndexlist = new int[]{0,1,2,3,4,5,6,7,8};//���̏��Ԃ̃��X�g

    public static void shuffle(int[] array) {
        // �z�񂪋󂩂P�v�f�Ȃ�V���b�t�����悤���Ȃ��̂̂ŁA���̂܂�return
        if (array.length <= 1) {
            return;
        }

        // Fisher?Yates shuffle
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // �v�f����ւ�(swap)
            int tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }

    public static void printArray(int[] array, String headerComment) {
        System.out.printf("%s -> %s\n\n", headerComment, Arrays.toString(array));
    }
}
