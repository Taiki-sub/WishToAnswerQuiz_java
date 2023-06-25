import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;//Timerの利用時に必要

import java.io.File;//音楽再生時に必要
import javax.sound.sampled.AudioFormat;//音楽再生時に必要
import javax.sound.sampled.AudioSystem;//音楽再生時に必要
import javax.sound.sampled.Clip;//音楽再生時に必要
import javax.sound.sampled.DataLine;//音楽再生時に必要

public class SoundPlayer{
        private AudioFormat format = null;
        private DataLine.Info info = null;
        private Clip clip = null;
        boolean stopFlag = false;
        Thread soundThread = null;
        private boolean loopFlag = false;

        public SoundPlayer(String pathname){
            File file = new File(pathname);
            try{
                format = AudioSystem.getAudioFileFormat(file).getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(AudioSystem.getAudioInputStream(file));
                //clip.setLoopPoints(0,clip.getFrameLength());//無限ループとなる
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public void SetLoop(boolean flag){
            loopFlag = flag;
        }

        public void play(){
            soundThread = new Thread(){
                public void run(){
                    stopFlag = false;
                    long time = (long)clip.getFrameLength();//44100で割ると再生時間（秒）がでる
                    long endTime = System.currentTimeMillis()+time*1000/44100;
                    clip.start();
                    while(true){
                        if(stopFlag){//stopFlagがtrueになった終了
                            clip.stop();
                            return;
                        }
                        if(endTime < System.currentTimeMillis()){//曲の長さを過ぎたら終了
                            if(loopFlag) {
                                clip.loop(1);//無限ループとなる
                            } else {
                                clip.stop();
                                return;
                            }
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            soundThread.start();
        }

        public void stop(){
            stopFlag = true;
            System.out.println("StopSound");
        }

    }