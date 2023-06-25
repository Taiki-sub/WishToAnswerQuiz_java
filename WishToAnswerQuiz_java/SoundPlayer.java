import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;//Timer�̗��p���ɕK�v

import java.io.File;//���y�Đ����ɕK�v
import javax.sound.sampled.AudioFormat;//���y�Đ����ɕK�v
import javax.sound.sampled.AudioSystem;//���y�Đ����ɕK�v
import javax.sound.sampled.Clip;//���y�Đ����ɕK�v
import javax.sound.sampled.DataLine;//���y�Đ����ɕK�v

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
                //clip.setLoopPoints(0,clip.getFrameLength());//�������[�v�ƂȂ�
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
                    long time = (long)clip.getFrameLength();//44100�Ŋ���ƍĐ����ԁi�b�j���ł�
                    long endTime = System.currentTimeMillis()+time*1000/44100;
                    clip.start();
                    while(true){
                        if(stopFlag){//stopFlag��true�ɂȂ����I��
                            clip.stop();
                            return;
                        }
                        if(endTime < System.currentTimeMillis()){//�Ȃ̒������߂�����I��
                            if(loopFlag) {
                                clip.loop(1);//�������[�v�ƂȂ�
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