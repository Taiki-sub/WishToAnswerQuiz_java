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

import java.awt.Font;


import java.util.TimerTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import java.io.File;//���y�Đ����ɕK�v
import javax.sound.sampled.AudioFormat;//���y�Đ����ɕK�v
import javax.sound.sampled.AudioSystem;//���y�Đ����ɕK�v
import javax.sound.sampled.Clip;//���y�Đ����ɕK�v
import javax.sound.sampled.DataLine;//���y�Đ����ɕK�v

//�Q�[����ʂ𓮂����Ă���N���X
public class GamePanel extends JPanel /*implements MouseListener*/{
    private static final long serialVersionUID = 1L;
    //�R���|�[�l���g
    JLabel gameLabel;
    JLabel pleasepush;
    PlayerLabel playerlabel;
    ButtonLabel buttonlabel;
    EnemyLabel enemylabel;
    QuizLabel quizlabel;
    P_QuizPointLabel p_QuizPointLabel;
    E_QuizPointLabel e_QuizPointLabel;
    P_QuizPlayerLabel p_QuizPlayerLabel;
    E_QuizPlayerLabel e_QuizPlayerLabel;
    YouLabel youLabel;
    Choice01Label choice01Label;
    Choice02Label choice02Label;
    Choice03Label choice03Label;
    SelectIconLabel selectIconLabel;
    CorrectLabel correctLabel;
    IncorrectLabel incorrectLabel;
    SoundPlayer theSoundPlayer1;
    SoundPlayer theSoundPlayer2;
    SoundPlayer HitSoundPlayer;
    JButton joinButton;
    WallLabel wallLabel;
    MyKeyListener myKeyListener;
    Maze Maze;
    PrintWriter out;//�o�͗p�̃��C�^�[
    boolean canMove;
    public int youNumber;

    GamePanel(){
        /*�p�l���T�C�Y�Ɠ\��t���ʒu�̐ݒ�͕s�v*/
        this.setLayout(null); //���C�A�E�g�̐ݒ�
        this.setBackground(Color.white); //�w�i�̐F
        //���̑��̒ǉ��@�\�͂����ɒǉ�   
         
    }

    public void signalStart(){
        //���O�̓��̓_�C�A���O���J��
		String myName = JOptionPane.showInputDialog(null,"���O����͂��Ă�������","���O�̓���",JOptionPane.QUESTION_MESSAGE);
		if(myName.equals("")){
			myName = "No name";//���O���Ȃ��Ƃ��́C"No name"�Ƃ���
		}
		String IP = JOptionPane.showInputDialog(null,"IP�A�h���X����͂��Ă�������","IP�A�h���X�̓���",JOptionPane.QUESTION_MESSAGE);
		if(IP.equals("")){
			myName = "localhost";
		}

		//�T�[�o�ɐڑ�����
		Socket socket = null;
		try {
			//"localhost"�́C���������ւ̐ڑ��Dlocalhost��ڑ����IP Address�i"133.42.155.201"�`���j�ɐݒ肷��Ƒ���PC�̃T�[�o�ƒʐM�ł���
			//10000�̓|�[�g�ԍ��DIP Address�Őڑ�����PC�����߂āC�|�[�g�ԍ��ł���PC�㓮�삷��v���O��������肷��
			socket = new Socket(IP, 10000);
		} catch (UnknownHostException e) {
			System.err.println("�z�X�g�� IP �A�h���X������ł��܂���: " + e);
		} catch (IOException e) {
			 System.err.println("�G���[���������܂���: " + e);
		}
		
		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//��M�p�̃X���b�h���쐬����
		mrt.start();//�X���b�h�𓮂����iRun�������j
        
    }

    //���b�Z�[�W��M�̂��߂̃X���b�h
	public class MesgRecvThread extends Thread {
		
		Socket socket;
		String myName;
		
		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}
		
		//�ʐM�󋵂��Ď����C��M�f�[�^�ɂ���ē��삷��
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//�ڑ��̍ŏ��ɖ��O�𑗂�
                //prepareComponets();
				String myNumberStr = br.readLine();
				int myNumberInt =  Integer.parseInt(myNumberStr);
                Maze.myNumber = myNumberInt % 2;
                Maze = new Maze();
                //���H��mynumber���P�̐l�������ݒ肵�āA�T�[�o�[�ɑ���i��l�����ƃR���t���N�g���N���邩��j
                if(Maze.myNumber == 1){
                    //���H���Ǘ����Ă���o�C�i���̃��X�g
                    int[][] list = new int[Maze.Masu][Maze.Masu]; 
                    CreateMaze c = new CreateMaze();
                    //���̊֐��ɓ��͂���ƁA���H�̃o�C�i���f�[�^�ɂȂ��Ă���B�i�Ԃ�l�͂Ȃ��j
                    c.createMaze(list);
                    //�l�ݖh�~
                    list[19][1] = 0;
                    list[1][18] = 0;

                    //�{�^���̈ʒu���J���Ēu��
                    list[Maze.Button_X_Index][Maze.Button_Y_Index] = 0;

                    //�T�[�o�Ɉ�x���邽�߂Ɉ�xString�^�ɕϊ�
                    String listmsg="";
                    for(int i = 0 ; i < Maze.Masu ; i++){
                        for(int k = 0; k < Maze.Masu ; k++){
                            listmsg = listmsg + Integer.toString(list[i][k]);
                        }   
                    }

                    String msg = "CreateMaze"+" "+listmsg;
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����

                    //�N�C�Y�̏o�肳��鏇�Ԃ��V���b�t�����邽�߂̊֐��Ƀ��X�g����́B�����_���ȏ��ԂɂȂ��Ă���B
                    Quiz.shuffle(Quiz.quizIndexlist);

                    //�N�C�Y�̏o�肳��鏇�Ԃ𓯊������邽�߂�String�^�Ɉ�x�ϊ����Ă���T�[�o�ɑ��M
                    listmsg="";
                    for(int i = 0 ; i < Quiz.quizIndexlist.length ; i++){
                        listmsg = listmsg + Integer.toString(Quiz.quizIndexlist[i]);      
                    }

                    //�N�C�Y�p�l���̈ʒu�Ƒ��x�������_���ɐݒ�
                    int x = new java.util.Random().nextInt(3000 -quizlabel.getWidth()- 500);
                    int y = new java.util.Random().nextInt(2000 -quizlabel.getHeight() - 500);
                    int xVelocity =  new java.util.Random().nextInt(10) ;
                    int yVelocity =  new java.util.Random().nextInt(10) ;

                    //�N�C�Y�p�l���̈ʒu�Ƒ��x�𓯊������邽�߂ɃT�[�o�ɑ��M
                    String vector = String.valueOf(x) + " " + String.valueOf(y) + " "+ String.valueOf(xVelocity) + " " + String.valueOf(yVelocity);
                    msg = "SetQuiz"+" "+listmsg + " " + Quiz.quizIndexlist.length + " " + vector;
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j���� 
                }

                if(Maze.myNumber == 0){
                    //joinButton.setBounds(2400,1400,300,150);
                }

                //myNumber�����܂��Ă���YouLabel�̈ʒu��ݒ�
                youLabel = new YouLabel();
                setYouLabel();
				
				while(true) {
					String inputLine = br.readLine();//�f�[�^����s�������ǂݍ���ł݂�
					if (inputLine != null) {//�ǂݍ��񂾂Ƃ��Ƀf�[�^���ǂݍ��܂ꂽ���ǂ������`�F�b�N����
						String[] inputTokens = inputLine.split(" ");	//���̓f�[�^����͂��邽�߂ɁA�X�y�[�X�Ő؂蕪����
						String cmd = inputTokens[0];//�R�}���h�̎��o���D�P�ڂ̗v�f�����o��

                        //�A�C�R���𓮂������߂̏���
						if(cmd.equals("MOVE")){
                            String direction = inputTokens[1];
                            int user =  Integer.parseInt(inputTokens[2]);
                            //�����̃v���C���[�̃A�C�R���̏���
                            if(user == 1){
                                if(direction.equals("up")){
                                    playerlabel.setLocation(playerlabel.getX(),playerlabel.getY()-(Maze.WallSize / 2));
                                }else if(direction.equals("down")){
                                    playerlabel.setLocation(playerlabel.getX(),playerlabel.getY()+(Maze.WallSize  / 2));
                                }else if(direction.equals("left")){
                                    playerlabel.setLocation(playerlabel.getX()-(Maze.WallSize / 2),playerlabel.getY());
                                }else if(direction.equals("right")){
                                    playerlabel.setLocation(playerlabel.getX()+(Maze.WallSize / 2),playerlabel.getY());
                                }           
                            }
                            //�E���̃v���C���[�̃A�C�R���̏���
                            else if(user == 0){
                                if(direction.equals("up")){
                                    enemylabel.setLocation(enemylabel.getX(),enemylabel.getY()-(Maze.WallSize / 2));
                                }else if(direction.equals("down")){
                                    enemylabel.setLocation(enemylabel.getX(),enemylabel.getY()+(Maze.WallSize / 2));
                                }else if(direction.equals("left")){
                                    enemylabel.setLocation(enemylabel.getX()-(Maze.WallSize / 2),enemylabel.getY());
                                }else if(direction.equals("right")){
                                    enemylabel.setLocation(enemylabel.getX()+(Maze.WallSize / 2),enemylabel.getY());
                                }
                            }
                            //�ʒu��ύX���邲�Ƃɍĕ`��
                            repaint();
                        }

                        //���H��ݒ肷�鏈��
                        if(cmd.equals("CreateMaze")){
                            String msg = inputTokens[1];
                            //�T�[�o���瑗���Ă���String�^�̃f�[�^��int�^�ɕύX
                            for(int i = 0 ; i < Maze.Masu ; i++){
                                for(int k = 0; k < Maze.Masu ; k++){
                                    int index = (i * Maze.Masu) + k; 
                                    int num = Character.getNumericValue(msg.charAt(index));
                                    Maze.list[i][k] = num;
                                }
                            }

                            Maze.WallList = new ArrayList<>();
                            int count = 0;
                            //���X�g�łP�ɂȂ��Ă���Ƃ���ɕǂ𐶐�
                            for(int i = 0 ; i < Maze.Masu ; i++){
                                for(int k = 0; k < Maze.Masu ; k++){
                                    if(Maze.list[i][k] == 1){
                                        Maze.box = new Rectangle(Maze.Start_wall_X + (i * Maze.WallSize) ,Maze.Start_wall_Y + (k * Maze.WallSize),Maze.WallSize,Maze.WallSize);
                                        //Maze.box02 = new WallLabel();
                                        //Maze.box02.setBounds(Maze.Start_wall_X + (i * Maze.WallSize) ,Maze.Start_wall_Y + (k * Maze.WallSize),Maze.WallSize,Maze.WallSize);
                                        Maze.WallList.add(Maze.box);
                                        count++;
                                    }
                                }
                            }
                            //�N�C�Y���ݒ肳�ꂽ�珉���ʒu�ɖ߂�
                            playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);
                            repaint();
                        }
                        
                        //�����邩�ǂ��������Ǘ����Ă��鏈��
                        if(cmd.equals("Canmove++")){
                            Maze.canmove++;
                            if(Maze.canmove > 3){
                                pleasepush.setVisible(false);
                            }
                        }

                        //�N�C�Y�̐ݒ�̏���
                        if(cmd.equals("SetQuiz")){
                            String msg = inputTokens[1];
                            //�N�C�Y���x���̈ʒu�Ƒ��x�𓯊�
                            int x = Integer.parseInt(inputTokens[3]);
                            int y = Integer.parseInt(inputTokens[4]);
                            int xVelocity = Integer.parseInt(inputTokens[5]);
                            int yVelocity = Integer.parseInt(inputTokens[6]);
                            int length = Integer.parseInt(inputTokens[2]);
                            //�N�C�Y�̏��Ԃ𓯊�
                            for(int i = 0 ; i < length ; i++){ 
                                int num = Character.getNumericValue(msg.charAt(i));
                                Quiz.quizIndexlist[i] = num;
                            }
                            quizlabel.x = x;
                            quizlabel.y = y;
                            quizlabel.xVelocity = xVelocity;
                            quizlabel.yVelocity = yVelocity;
                            quizlabel.setLocation(quizlabel.x,quizlabel.y);

                            //�N�C�Y�̏��Ԃ������ł��Ă���I�����Ɩ�蕶�𓯊�
                            choice01Label.setchoice01();
                            choice02Label.setchoice02();
                            choice03Label.setchoice03();
                            quizlabel.setQuizText();
                        }
                        //�N�����{�^���ɓ��B�����Ƃ��̏���
                        if(cmd.equals("Touchbutton")){
                            int number =  Integer.parseInt(inputTokens[1]);
                            //�{�^���ɓ��B���Ă��Ȃ��v���C���[�͓����Ȃ�����iMaze.canmove > 3�œ�����j
                            if(Maze.myNumber != number){
                               Maze.canmove = 3; 
                            }
                            Quiz.canAnswer = true;
                            //�N�C�Y���x������U��~
                            quizlabel.xVelocity  = 0;
                            quizlabel.yVelocity = 0;
                            //�I������������悤�ɂ���
                            choice01Label.setVisible(true);
                            choice02Label.setVisible(true);
                            choice03Label.setVisible(true);
                            selectIconLabel.setVisible(true);
                            //��蕶�͌����Ȃ�����
                            quizlabel.setVisible(false);
                            //���y�͈�U�~�߂�
                            Quiz.timerSoundPlayer.stop();
                            theSoundPlayer1 = new SoundPlayer("button.wav");
                            theSoundPlayer1.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                            theSoundPlayer1.play(); 
                        }

                        //�{�^���𗣂ꂽ�Ƃ��̏���
                        if(cmd.equals("ReleaseButton")){
                            int number =  Integer.parseInt(inputTokens[1]);
                            //�����Ȃ����v���C���[�𓮂���悤�ɂ���
                            if(Maze.myNumber != number){
                               Maze.canmove = 4; 
                            }
                            if(Maze.myNumber == 1){
                                int xVelocity =  new java.util.Random().nextInt(10) ;
                                int yVelocity =  new java.util.Random().nextInt(10) ;
                                String msg = "SetVelocity" + " " + String.valueOf(xVelocity) + " " + String.valueOf(yVelocity);
                                //�T�[�o�ɏ��𑗂�
                                out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                                out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                            }
                            Quiz.canAnswer = false;
                            choice01Label.setVisible(false);
                            choice02Label.setVisible(false);
                            choice03Label.setVisible(false);
                            selectIconLabel.setVisible(false);
                            quizlabel.setVisible(true);
                            Quiz.timerSoundPlayer.play();
                        }
                        if(cmd.equals("SetVelocity")){
                            int xVelocity = Integer.parseInt(inputTokens[1]);
                            int yVelocity = Integer.parseInt(inputTokens[2]);
                            quizlabel.xVelocity = xVelocity;
                            quizlabel.yVelocity = yVelocity;
                        }
                        if(cmd.equals("P_RESTART")){
                            playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            HitSoundPlayer = new SoundPlayer("hit.wav");
                            HitSoundPlayer.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                            HitSoundPlayer.play(); 
                        }
                        if(cmd.equals("E_RESTART")){
                            enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);
                            HitSoundPlayer= new SoundPlayer("hit.wav");
                            HitSoundPlayer.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                            HitSoundPlayer.play();
                        }
                        if(cmd.equals("Select01")){
                            selectIconLabel.setLocation(300,1500); 
                            SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER01;
                        }
                        if(cmd.equals("Select02")){
                            selectIconLabel.setLocation(1200,1500); 
                            SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER02;
                        }
                        if(cmd.equals("Select03")){
                            selectIconLabel.setLocation(2000,1500); 
                            SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER03;
                        }
                        if(cmd.equals("Seikai")){
                            int number =  Integer.parseInt(inputTokens[1]);
                            theSoundPlayer1 = new SoundPlayer("seikai.wav");
                            theSoundPlayer1.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                            theSoundPlayer1.play();
                            Quiz.timerSoundPlayer.SetLoop(true);
                            Quiz.timerSoundPlayer.play();
                            //�����Ȃ�
                            correctLabel.setVisible(true);//�����̕����\��
                            correctLabel.setVisible(false);
                            //�����Ȃ�S���̈ʒu�������ʒu�ɖ߂�
                            playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);

                            //�����҂Ƀ|�C���g���Z
                            if(number == 1){
                                Quiz.p_point++;
                                p_QuizPointLabel.setText(String.valueOf(Quiz.p_point / 2));
                            }else if(number == 0){
                                Quiz.e_point++;
                                e_QuizPointLabel.setText(String.valueOf(Quiz.e_point / 2));
                            }
                            if(Quiz.p_point == 4){
                                if(Maze.myNumber == 0){
                                    //�܂�
                                    quizlabel.xVelocity = 0;
                                    quizlabel.yVelocity = 0;
                                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.LOSER);
                                    theSoundPlayer2 = new SoundPlayer("laughter.wav");
                                    theSoundPlayer2.SetLoop(true);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                                    theSoundPlayer2.play(); 
                                } else if(Maze.myNumber == 1){
                                    //����
                                    quizlabel.xVelocity = 0;
                                    quizlabel.yVelocity = 0;
                                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.WINNER);
                                    theSoundPlayer2 = new SoundPlayer("win.wav");
                                    theSoundPlayer2.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                                    theSoundPlayer2.play();
                                }
                            }
                            if(Quiz.e_point == 4){
                                if(Maze.myNumber == 0){
                                    //����
                                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.WINNER);
                                    theSoundPlayer2 = new SoundPlayer("win.wav");
                                    theSoundPlayer2.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                                    theSoundPlayer2.play();
                                } else if(Maze.myNumber == 1){
                                    //����
                                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.LOSER);
                                    theSoundPlayer2 = new SoundPlayer("laughter.wav");
                                    theSoundPlayer2.SetLoop(true);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                                    theSoundPlayer2.play();
                                }
                            }
                            
                            //�N�C�Y�X�V
                            Quiz.quizindex ++;
                            quizlabel.setText(Quiz.quiztextlist[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice01Label.setText("1:"  + Quiz.choice01list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice01Label.setBounds(100,1700,(100 * Quiz.choice01list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20); 
                            choice02Label.setText("2:"  + Quiz.choice02list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice02Label.setBounds(1000,1700,(100 * Quiz.choice02list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20);
                            choice03Label.setText("3:"  + Quiz.choice03list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice03Label.setBounds(1900,1700,(100 * Quiz.choice03list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20);
                            
                            
                        String msg = "ReleaseButton" + " " + Maze.myNumber;
                        //�T�[�o�ɏ��𑗂�
                        out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                        out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                            
                        }
                        if(cmd.equals("Huseikai")){
                            int number =  Integer.parseInt(inputTokens[1]);                            
                            //�s�����Ȃ�
                            theSoundPlayer1 = new SoundPlayer("huseikai.wav");
                            theSoundPlayer1.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                            theSoundPlayer1.play();
                            Quiz.timerSoundPlayer.SetLoop(true);
                            Quiz.timerSoundPlayer.play();
                            incorrectLabel.setVisible(false);
                            //�s�����Ȃ�Ԉ�����l���������ʒu�ɖ߂�
                            if(number == 1){
                                playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            }else if(number == 0){
                                enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);
                            } 
                            Quiz.canAnswer = false;
                            choice01Label.setVisible(false);
                            choice02Label.setVisible(false);
                            choice03Label.setVisible(false);
                            selectIconLabel.setVisible(false);
                            quizlabel.setVisible(true);
                        }
					}else{
						break;
					}

				}
				socket.close();
			} catch (IOException e) {
				System.err.println("�G���[���������܂���: " + e);
			}
		}
	}

    public void prepareComponets(){
        /*
        gameLabel = new JLabel();
        gameLabel.setText("�Q�[�����");
        gameLabel.setBounds(100,200,100,30);
        this.add(gameLabel);
        */

        pleasepush = new JLabel();
        pleasepush.setText("�X�������Ă�!");
        pleasepush.setFont(new Font("MV boil",Font.BOLD,100));
        pleasepush.setHorizontalTextPosition(JLabel.CENTER);
        pleasepush.setVerticalTextPosition(JLabel.BOTTOM);
        pleasepush.setOpaque(true);
        pleasepush.setBackground(Color.orange);
        pleasepush.setBounds(1200,1700,1000,100);
        this.add(pleasepush);

        //�v���C���[�̃��x���̏���
        playerlabel = new PlayerLabel();
        this.add(playerlabel);
        myKeyListener = new MyKeyListener(this);

        buttonlabel = new ButtonLabel();
        this.add(buttonlabel);

        enemylabel = new EnemyLabel();
        this.add(enemylabel);
        myKeyListener = new MyKeyListener(this);

        

        p_QuizPointLabel = new P_QuizPointLabel();
        this.add(p_QuizPointLabel);

        e_QuizPointLabel = new E_QuizPointLabel();
        this.add(e_QuizPointLabel);

        p_QuizPlayerLabel = new P_QuizPlayerLabel();
        this.add(p_QuizPlayerLabel);

        e_QuizPlayerLabel = new E_QuizPlayerLabel();
        this.add(e_QuizPlayerLabel);

        //Quiz.shuffle(Quiz.quizIndexlist);

        quizlabel = new QuizLabel();
        this.add(quizlabel);
        vitalizeCat(quizlabel);

        
        choice01Label = new Choice01Label();
        choice01Label.setVisible(false);
        this.add(choice01Label);
        
        choice02Label = new Choice02Label();
        choice02Label.setVisible(false);
        this.add(choice02Label);

        choice03Label = new Choice03Label();
        choice03Label.setVisible(false);
        this.add(choice03Label);
        

        selectIconLabel = new SelectIconLabel();
        selectIconLabel.setVisible(false);
        this.add(selectIconLabel);

        correctLabel = new CorrectLabel();
        correctLabel.setVisible(false);
        this.add(correctLabel);

        incorrectLabel = new IncorrectLabel();
        incorrectLabel.setVisible(false);
        this.add(incorrectLabel);

        //�p�X�{�^���̐ݒ�
        /*
        joinButton = new JButton();//�{�^���ɃA�C�R����ݒ肷��
        setjoinButton();
        */

        //youLabel = new YouLabel();
        //this.add(youLabel);


        
        /*
        Maze = new Maze();
        CreateMaze c = new CreateMaze();
        c.createMaze(Maze.list);
        
        //�l�ݖh�~
        Maze.list[19][1] = 0;
        Maze.list[1][18] = 0;

        //�{�^���̈ʒu���J���Ēu��
        Maze.list[Maze.Button_X_Index][Maze.Button_Y_Index] = 0;
        */

        

        /*
        int count = 0;
        //���X�g�łP�ɂȂ��Ă���Ƃ���ɕǂ𐶐�
        for(int i = 0 ; i < Maze.Masu ; i++){
            for(int k = 0; k < Maze.Masu ; k++){
                if(Maze.list[i][k] == 1){
                    Maze.box = new Rectangle(Maze.Start_wall_X + (i * Maze.WallSize) ,Maze.Start_wall_Y + (k * Maze.WallSize),Maze.WallSize,Maze.WallSize);
                    Maze.WallList.add(Maze.box);
                    count++;
                }
            }
        }
        */
    }

     public void setYouLabel(){
        this.add(youLabel);
        youLabel.repaint();
     }

    public void setjoinButton(){
        joinButton.setBounds(300,1400,300,150);
        joinButton.setText("�Q��");
        joinButton.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 100));
        //joinButton.addMouseListener(this);
        //joinButton.repaint();
        this.add(joinButton);
    }

    public void canmovePlus(){
        String msg = "Canmove++"+" "+ Maze.myNumber;
        //�T�[�o�ɏ��𑗂�
        out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
        out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
    }

    private class MyKeyListener implements KeyListener{
        //�\��t����ێ�
        GamePanel panel;

        //�R���X�g���N�^
        MyKeyListener(GamePanel p){
            super();
            panel = p;
            p.addKeyListener(this);
        }
    
        //�L�[�̐ݒ�
        @Override
        public void keyTyped(KeyEvent e) {
            String direction_input = "";
            //player���̑���
            if((e.getKeyChar()) == '9'){
                canmovePlus();
                pleasepush.setText("������Ƒ҂��ĂˁI");
            }
            if(Maze.canmove > 3){
                if(Maze.myNumber == 1){
                    switch(e.getKeyChar()){
                    //��
                    case 'i':
                    case 'I':
                        if(!(isHit(playerlabel.getX(),playerlabel.getY()-Maze.WallSize))){
                            if(playerlabel.getY() > 0 ){
                                direction_input = "up";
                            }
                        }
                        break;
                    //��
                    case 'k':
                    case 'K':
                        if(!(isHit(playerlabel.getX(),playerlabel.getY()+Maze.WallSize))){
                            if(playerlabel.getY() + playerlabel.playerImage.getHeight(null) < panel.getHeight() ){
                                direction_input = "down";
                            }
                        }
                        break;
                    //��
                    case 'j':
                    case 'J':
                        if(!(isHit(playerlabel.getX()-Maze.WallSize,playerlabel.getY()))){
                            if(playerlabel.getX() > 0 ){
                                direction_input = "left";
                            }
                        }
                        break;
                    //�E
                    case 'L':
                    case 'l':
                        if(!(isHit(playerlabel.getX()+Maze.WallSize,playerlabel.getY()))){
                            if(playerlabel.getX()  /* + playerlabel.getHeight(null)*/ < panel.getWidth() ){
                                direction_input = "right";
                            }
                        }
                        break;
                    }
                }
                //============================================================================================================
                //enemy���̑���
                else if(Maze.myNumber == 0){
                    switch(e.getKeyChar()){
                    //��
                    case 'i':
                    case 'I':
                        if(!(isHit(enemylabel.getX(),enemylabel.getY()-Maze.WallSize))){
                            if(enemylabel.getY() > 0 ){
                                direction_input = "up";
                            }
                        }
                        break;
                    //��
                    case 'k':
                    case 'K':
                        if(!(isHit(enemylabel.getX(),enemylabel.getY()+Maze.WallSize))){
                            if(enemylabel.getY() + enemylabel.enemyImage.getHeight(null) < panel.getHeight() ){
                                direction_input = "down";
                            }
                        }
                        break;
                    //��
                    case 'j':
                    case 'J':
                        if(!(isHit(enemylabel.getX()-Maze.WallSize,enemylabel.getY()))){
                            if(enemylabel.getX() > 0 ){
                                direction_input = "left";
                            }
                        }
                        break;
                    //�E
                    case 'L':
                    case 'l':
                        if(!(isHit(enemylabel.getX()+Maze.WallSize,enemylabel.getY()))){
                            if(enemylabel.getX() + enemylabel.enemyImage.getHeight(null) < panel.getWidth() ){
                                direction_input = "right";
                            }
                        }
                        break;
                    }

                }
            
            }
            if(Quiz.canAnswer){
                switch(e.getKeyChar()){
                case '1':
                    String msg01 = "Select01";
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg01);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                    /*
                    selectIconLabel.setLocation(300,1500); 
                    SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER01;
                    */
                break;
                case '2':
                    String msg02 = "Select02";
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg02);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                    /*
                    selectIconLabel.setLocation(1200,1500); 
                    SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER02;
                    */
                break;
                case '3':
                    String msg03 = "Select03";
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg03);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                    /*
                    selectIconLabel.setLocation(2000,1500); 
                    SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER03; 
                    */
                break;
                case '4':
                    if(Quiz.DoAnswer){
                        int answer = 99;
                        //�I��ł���I�����ԍ��擾
                        if(SelectIconLabel.cheakSelect == SelectIconLabel.Select.ANSWER01){
                            answer = 0;  
                        }else if(SelectIconLabel.cheakSelect == SelectIconLabel.Select.ANSWER02){
                            answer = 1;
                        }else if(SelectIconLabel.cheakSelect == SelectIconLabel.Select.ANSWER03){
                            answer = 2;
                        } else{
                            System.out.println("answererror");
                        }

                        //���딻��
                        if(Quiz.answerindexlist[Quiz.quizIndexlist[Quiz.quizindex]]== answer){
                            String seikai = "Seikai" + " " + Maze.myNumber;
                            //�T�[�o�ɏ��𑗂�
                            out.println(seikai);//���M�f�[�^���o�b�t�@�ɏ����o��
                            out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                            /*
                            theSoundPlayer1 = new SoundPlayer("seikai.wav");
                            theSoundPlayer1.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                            theSoundPlayer1.play();
                            Quiz.timerSoundPlayer.SetLoop(true);
                            Quiz.timerSoundPlayer.play();
                            //�����Ȃ�
                            correctLabel.setVisible(true);//�����̕����\��
                            correctLabel.setVisible(false);
                            //�����Ȃ�S���̈ʒu�������ʒu�ɖ߂�
                            playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);

                            //�����҂Ƀ|�C���g���Z
                            if(Maze.myNumber ==1){
                                Quiz.p_point++; 
                                p_QuizPointLabel.setText(String.valueOf(Quiz.p_point));
                                System.out.println(Quiz.p_point);
                            }else if(Maze.myNumber == 0){
                                Quiz.e_point++;
                                e_QuizPointLabel.setText(String.valueOf(Quiz.e_point));
                                System.out.println(Quiz.p_point);
                                System.out.println(Quiz.e_point);
                            } 
                            
                            //�N�C�Y�X�V
                            Quiz.quizindex ++;
                            quizlabel.setText(Quiz.quiztextlist[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice01Label.setText("1:"  + Quiz.choice01list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice01Label.setBounds(100,1700,(100 * Quiz.choice01list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20); 
                            choice02Label.setText("2:"  + Quiz.choice02list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice02Label.setBounds(1000,1700,(100 * Quiz.choice02list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20);
                            choice03Label.setText("3:"  + Quiz.choice03list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice03Label.setBounds(1900,1700,(100 * Quiz.choice03list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20);
                            */

                            /*
                            //���H�č\�z
                            Maze = new Maze();
                            CreateMaze c = new CreateMaze();
                            c.createMaze(Maze.list);
                            //�l�ݖh�~
                            Maze.list[19][1] = 0;
                            Maze.list[1][18] = 0;
                            Maze.list[Maze.Button_X_Index][Maze.Button_Y_Index] = 0;
                            int count = 0;
                            Maze.WallList = new ArrayList<>();
                            //���X�g�łP�ɂȂ��Ă���Ƃ���ɕǂ𐶐�
                            for(int i = 0 ; i < Maze.Masu ; i++){
                                for(int k = 0; k < Maze.Masu ; k++){
                                    if(Maze.list[i][k] == 1){
                                        Maze.box = new Rectangle(Maze.Start_wall_X + (i * Maze.WallSize) ,Maze.Start_wall_Y + (k * Maze.WallSize),Maze.WallSize,Maze.WallSize);
                                        Maze.WallList.add(Maze.box);
                                        count++;
                                    }
                                }
                            }
                            */ 
                            Maze = new Maze();
                            int[][] list = new int[Maze.Masu][Maze.Masu]; 
                            CreateMaze c = new CreateMaze();
                            c.createMaze(list);
                            
                            //�l�ݖh�~
                            list[19][1] = 0;
                            list[1][18] = 0;

                            //�{�^���̈ʒu���J���Ēu��
                            list[Maze.Button_X_Index][Maze.Button_Y_Index] = 0;
                            String listmsg="";
                            for(int i = 0 ; i < Maze.Masu ; i++){
                                for(int k = 0; k < Maze.Masu ; k++){
                                    listmsg = listmsg + Integer.toString(list[i][k]);
                                }   
                            }
                            String msg = "CreateMaze"+" "+listmsg;
                            //�T�[�o�ɏ��𑗂�
                            out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                            out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                        

                        }else{
                            String huseikai = "Huseikai" + " " + Maze.myNumber;
                            //�T�[�o�ɏ��𑗂�
                            out.println(huseikai);//���M�f�[�^���o�b�t�@�ɏ����o��
                            out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                            /*
                            /*
                            //�s�����Ȃ�
                            theSoundPlayer1 = new SoundPlayer("huseikai.wav");
                            theSoundPlayer1.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                            theSoundPlayer1.play();
                            Quiz.timerSoundPlayer.SetLoop(true);
                            Quiz.timerSoundPlayer.play();
                            incorrectLabel.setVisible(false);
                            //�s�����Ȃ�Ԉ�����l���������ʒu�ɖ߂�
                            if(Maze.myNumber ==1){
                                playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            }else if(Maze.myNumber == 0){
                                enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);
                            } 
                            */
                        }  
                    }   
                break; 
                } 
            }
            if(e.getKeyChar() == '5'){
                correctLabel.setVisible(false);
                incorrectLabel.setVisible(false); 
            }

        
                
            
             
            //==========================================================================================================
            touchButton();//���x�����{�^���ɐG��Ă���̂����L�[���삲�ƂɊĎ�
            

            String msg = "MOVE"+" "+ direction_input +" "+Maze.myNumber;
            //�T�[�o�ɏ��𑗂�
            out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
            out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
        }
        @Override
        public void keyReleased(KeyEvent e) {
            
        }
        @Override
        public void keyPressed(KeyEvent e) {//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏����i�N���b�N�Ƃ̈Ⴂ�ɒ��Ӂj
            
        
        }
       
    }
    
    //�ǂ̃O���t�B�b�N�̐ݒ�
    public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
        for(Rectangle r1 :Maze.WallList){
            g.setColor(Color.BLACK);
            g2.fill(r1);
            g2.draw(r1);
        }
        
    }

    public void vitalizeCat(QuizLabel c){
        CatActionListener catListener = new CatActionListener(c);
        c.timer = new Timer(10,catListener);
        this.add(c);
        c.timer.start();
    }

    private class CatActionListener implements ActionListener{

        private QuizLabel cat;

        public CatActionListener(QuizLabel c){
            cat = c;
        }

        @Override
        public void actionPerformed(ActionEvent c){
            if(Maze.canmove > 0){
                if(cat.x > 3000 - ((Quiz.q_fontsize * Quiz.quiztextlist[0].length()) + 20) || cat.x < 0){
                    cat.xVelocity = cat.xVelocity * (-1);
                }
                cat.x = cat.x + cat.xVelocity;
                if(cat.y > 2000  - (Quiz.q_fontsize* 3 + 20)|| cat.y < 0){
                    cat.yVelocity = cat.yVelocity * (-1);
                }
                cat.y = cat.y + cat.yVelocity;
                cat.setLocation(cat.x,cat.y);
                cat.repaint();
                int xLength = (Quiz.q_fontsize * Quiz.quiztextlist[Quiz.quizindex].length());
                int yLength = Quiz.q_fontsize + 20;
                int p_x = playerlabel.getX();
                int p_y = playerlabel.getY();
                int e_x = enemylabel.getX() ;
                int e_y = enemylabel.getY() ;
                if(p_x > cat.x  -50 && p_x < cat.x + xLength + 50 && p_y > cat.y -50 && p_y < cat.y + yLength + 50){
                    String msg = "P_RESTART";
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                }
                if(e_x> cat.x  -50 && e_x < cat.x + xLength + 50 && e_y > cat.y -50 && e_y < cat.y + yLength + 50){
                    String msg = "E_RESTART";
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                }
            }
            
        }

    }

    //�i���A���j�ɕǂ����邩�����m���郁�\�b�h
    private boolean isHit(int x, int y) {
        // (x,y)�ɕǂ���������Ԃ���
        if(Maze.myNumber == 1){
            if(playerlabel.getX() > 900 && playerlabel.getY() > 300){
                int i = ((x) - 900) / Maze.WallSize;//�������ɉ��}�X�ڂ�
                int k = ((y) - 300) / Maze.WallSize;//�c�����ɉ��}�X�ڂ�
                if(i <Maze.Masu && k < Maze.Masu){//�}�X�O�ł��v���C���[��������悤�ɂ��邽�߂ɓ���Ă�
                    if (Maze.list[i][k] == 1) {//�����ii,k)���P�Ȃ�
                        return true;//true��Ԃ�
                    }
                }
            }
        }
        else if(Maze.myNumber == 0){
            if(enemylabel.getX() > 900 && enemylabel.getY() > 300){
                int i = ((x) - 900) / Maze.WallSize;//�������ɉ��}�X�ڂ�
                int k = ((y) - 300) / Maze.WallSize;//�c�����ɉ��}�X�ڂ�
                if(i <Maze.Masu && k < Maze.Masu){//�}�X�O�ł��v���C���[��������悤�ɂ��邽�߂ɓ���Ă�
                    if (Maze.list[i][k] == 1) {//�����ii,k)���P�Ȃ�
                        return true;//true��Ԃ�
                    }
                }
            }
        }
        
        
        // �Ȃ���΂Ԃ���Ȃ�����false��Ԃ�
        return false;
    }

    //PlayerLabel��button�ɐڐG���Ă��邩�ǂ��������m���郁�\�b�h
    private void touchButton(){
        if(Maze.myNumber == 1){
            int index_x = (playerlabel.getX() - Maze.Start_wall_X)/60 ;
            int index_y = ((playerlabel.getY() - Maze.Start_wall_Y)/60);
            if(index_x == Maze.Button_X_Index && index_y == Maze.Button_Y_Index
               || index_x == Maze.Button_X_Index -1 && index_y == Maze.Button_Y_Index -1
               || index_x == Maze.Button_X_Index -1 && index_y == Maze.Button_Y_Index 
               || index_x == Maze.Button_X_Index -1 && index_y == Maze.Button_Y_Index + 1
               || index_x == Maze.Button_X_Index +1 && index_y == Maze.Button_Y_Index -1
               || index_x == Maze.Button_X_Index +1 && index_y == Maze.Button_Y_Index 
               || index_x == Maze.Button_X_Index +1 && index_y == Maze.Button_Y_Index + 1
               || index_x == Maze.Button_X_Index  && index_y == Maze.Button_Y_Index -1
               || index_x == Maze.Button_X_Index  && index_y == Maze.Button_Y_Index 
               || index_x == Maze.Button_X_Index  && index_y == Maze.Button_Y_Index + 1
               ){
                Quiz.DoAnswer = true;
                String msg = "Touchbutton"+" "+Maze.myNumber;
                //�T�[�o�ɏ��𑗂�
                out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                /*
                Quiz.canAnswer = true; 
                choice01Label.setVisible(true);
                choice02Label.setVisible(true);
                choice03Label.setVisible(true);
                selectIconLabel.setVisible(true);
                quizlabel.setVisible(false);
                Quiz.timerSoundPlayer.stop();
                theSoundPlayer1 = new SoundPlayer("button.wav");
                theSoundPlayer1.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                theSoundPlayer1.play();
                */

            }
            else{
                if(Quiz.DoAnswer){
                    String msg = "ReleaseButton"+" "+Maze.myNumber;
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                }
                Quiz.DoAnswer =false;
                    
                /*
                Quiz.canAnswer = false;
                choice01Label.setVisible(false);
                choice02Label.setVisible(false);
                choice03Label.setVisible(false);
                selectIconLabel.setVisible(false);
                quizlabel.setVisible(true);
                */
            }
        }else if(Maze.myNumber == 0){
            int index_x = (enemylabel.getX() - Maze.Start_wall_X)/60 ;
            int index_y = ((enemylabel.getY() - Maze.Start_wall_Y)/60);
            if(index_x == Maze.Button_X_Index && index_y == Maze.Button_Y_Index
               || index_x == Maze.Button_X_Index -1 && index_y == Maze.Button_Y_Index -1
               || index_x == Maze.Button_X_Index -1 && index_y == Maze.Button_Y_Index 
               || index_x == Maze.Button_X_Index -1 && index_y == Maze.Button_Y_Index + 1
               || index_x == Maze.Button_X_Index +1 && index_y == Maze.Button_Y_Index -1
               || index_x == Maze.Button_X_Index +1 && index_y == Maze.Button_Y_Index 
               || index_x == Maze.Button_X_Index +1 && index_y == Maze.Button_Y_Index + 1
               || index_x == Maze.Button_X_Index  && index_y == Maze.Button_Y_Index -1
               || index_x == Maze.Button_X_Index  && index_y == Maze.Button_Y_Index 
               || index_x == Maze.Button_X_Index  && index_y == Maze.Button_Y_Index + 1
               ){
                Quiz.DoAnswer = true;
                String msg = "Touchbutton"+" "+Maze.myNumber;
                //�T�[�o�ɏ��𑗂�
                out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                /*
                Quiz.canAnswer = true; 
                choice01Label.setVisible(true);
                choice02Label.setVisible(true);
                choice03Label.setVisible(true);
                selectIconLabel.setVisible(true);
                quizlabel.setVisible(false);
                Quiz.timerSoundPlayer.stop();
                theSoundPlayer1 = new SoundPlayer("button.wav");
                theSoundPlayer1.SetLoop(false);//�a�f�l�Ƃ��čĐ����J��Ԃ�
                theSoundPlayer1.play();
                */
            }
            else{
                if(Quiz.DoAnswer){
                    String msg = "ReleaseButton"+" "+Maze.myNumber;
                    //�T�[�o�ɏ��𑗂�
                    out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
                    out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
                }
                Quiz.DoAnswer =false;
                
                /*
                Quiz.canAnswer = false;
                choice01Label.setVisible(false);
                choice02Label.setVisible(false);
                choice03Label.setVisible(false);
                selectIconLabel.setVisible(false);
                quizlabel.setVisible(true);
                */
            }


        } 
    }
    
}