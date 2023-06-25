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

import java.io.File;//音楽再生時に必要
import javax.sound.sampled.AudioFormat;//音楽再生時に必要
import javax.sound.sampled.AudioSystem;//音楽再生時に必要
import javax.sound.sampled.Clip;//音楽再生時に必要
import javax.sound.sampled.DataLine;//音楽再生時に必要

//ゲーム画面を動かしているクラス
public class GamePanel extends JPanel /*implements MouseListener*/{
    private static final long serialVersionUID = 1L;
    //コンポーネント
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
    PrintWriter out;//出力用のライター
    boolean canMove;
    public int youNumber;

    GamePanel(){
        /*パネルサイズと貼り付け位置の設定は不要*/
        this.setLayout(null); //レイアウトの設定
        this.setBackground(Color.white); //背景の色
        //その他の追加機能はここに追加   
         
    }

    public void signalStart(){
        //名前の入力ダイアログを開く
		String myName = JOptionPane.showInputDialog(null,"名前を入力してください","名前の入力",JOptionPane.QUESTION_MESSAGE);
		if(myName.equals("")){
			myName = "No name";//名前がないときは，"No name"とする
		}
		String IP = JOptionPane.showInputDialog(null,"IPアドレスを入力してください","IPアドレスの入力",JOptionPane.QUESTION_MESSAGE);
		if(IP.equals("")){
			myName = "localhost";
		}

		//サーバに接続する
		Socket socket = null;
		try {
			//"localhost"は，自分内部への接続．localhostを接続先のIP Address（"133.42.155.201"形式）に設定すると他のPCのサーバと通信できる
			//10000はポート番号．IP Addressで接続するPCを決めて，ポート番号でそのPC上動作するプログラムを特定する
			socket = new Socket(IP, 10000);
		} catch (UnknownHostException e) {
			System.err.println("ホストの IP アドレスが判定できません: " + e);
		} catch (IOException e) {
			 System.err.println("エラーが発生しました: " + e);
		}
		
		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//受信用のスレッドを作成する
		mrt.start();//スレッドを動かす（Runが動く）
        
    }

    //メッセージ受信のためのスレッド
	public class MesgRecvThread extends Thread {
		
		Socket socket;
		String myName;
		
		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}
		
		//通信状況を監視し，受信データによって動作する
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//接続の最初に名前を送る
                //prepareComponets();
				String myNumberStr = br.readLine();
				int myNumberInt =  Integer.parseInt(myNumberStr);
                Maze.myNumber = myNumberInt % 2;
                Maze = new Maze();
                //迷路をmynumberが１の人が初期設定して、サーバーに送る（二人が作るとコンフリクトが起こるから）
                if(Maze.myNumber == 1){
                    //迷路を管理しているバイナリのリスト
                    int[][] list = new int[Maze.Masu][Maze.Masu]; 
                    CreateMaze c = new CreateMaze();
                    //この関数に入力すると、迷路のバイナリデータになっている。（返り値はない）
                    c.createMaze(list);
                    //詰み防止
                    list[19][1] = 0;
                    list[1][18] = 0;

                    //ボタンの位置を開けて置く
                    list[Maze.Button_X_Index][Maze.Button_Y_Index] = 0;

                    //サーバに一度送るために一度String型に変換
                    String listmsg="";
                    for(int i = 0 ; i < Maze.Masu ; i++){
                        for(int k = 0; k < Maze.Masu ; k++){
                            listmsg = listmsg + Integer.toString(list[i][k]);
                        }   
                    }

                    String msg = "CreateMaze"+" "+listmsg;
                    //サーバに情報を送る
                    out.println(msg);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する

                    //クイズの出題される順番をシャッフルするための関数にリストを入力。ランダムな順番になっている。
                    Quiz.shuffle(Quiz.quizIndexlist);

                    //クイズの出題される順番を同期させるためにString型に一度変換してからサーバに送信
                    listmsg="";
                    for(int i = 0 ; i < Quiz.quizIndexlist.length ; i++){
                        listmsg = listmsg + Integer.toString(Quiz.quizIndexlist[i]);      
                    }

                    //クイズパネルの位置と速度をランダムに設定
                    int x = new java.util.Random().nextInt(3000 -quizlabel.getWidth()- 500);
                    int y = new java.util.Random().nextInt(2000 -quizlabel.getHeight() - 500);
                    int xVelocity =  new java.util.Random().nextInt(10) ;
                    int yVelocity =  new java.util.Random().nextInt(10) ;

                    //クイズパネルの位置と速度を同期させるためにサーバに送信
                    String vector = String.valueOf(x) + " " + String.valueOf(y) + " "+ String.valueOf(xVelocity) + " " + String.valueOf(yVelocity);
                    msg = "SetQuiz"+" "+listmsg + " " + Quiz.quizIndexlist.length + " " + vector;
                    //サーバに情報を送る
                    out.println(msg);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する 
                }

                if(Maze.myNumber == 0){
                    //joinButton.setBounds(2400,1400,300,150);
                }

                //myNumberが決まってからYouLabelの位置を設定
                youLabel = new YouLabel();
                setYouLabel();
				
				while(true) {
					String inputLine = br.readLine();//データを一行分だけ読み込んでみる
					if (inputLine != null) {//読み込んだときにデータが読み込まれたかどうかをチェックする
						String[] inputTokens = inputLine.split(" ");	//入力データを解析するために、スペースで切り分ける
						String cmd = inputTokens[0];//コマンドの取り出し．１つ目の要素を取り出す

                        //アイコンを動かすための処理
						if(cmd.equals("MOVE")){
                            String direction = inputTokens[1];
                            int user =  Integer.parseInt(inputTokens[2]);
                            //左側のプレイヤーのアイコンの処理
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
                            //右側のプレイヤーのアイコンの処理
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
                            //位置を変更するごとに再描画
                            repaint();
                        }

                        //迷路を設定する処理
                        if(cmd.equals("CreateMaze")){
                            String msg = inputTokens[1];
                            //サーバから送られてきたString型のデータをint型に変更
                            for(int i = 0 ; i < Maze.Masu ; i++){
                                for(int k = 0; k < Maze.Masu ; k++){
                                    int index = (i * Maze.Masu) + k; 
                                    int num = Character.getNumericValue(msg.charAt(index));
                                    Maze.list[i][k] = num;
                                }
                            }

                            Maze.WallList = new ArrayList<>();
                            int count = 0;
                            //リストで１になっているところに壁を生成
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
                            //クイズが設定されたら初期位置に戻す
                            playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);
                            repaint();
                        }
                        
                        //動けるかどうかをを管理している処理
                        if(cmd.equals("Canmove++")){
                            Maze.canmove++;
                            if(Maze.canmove > 3){
                                pleasepush.setVisible(false);
                            }
                        }

                        //クイズの設定の処理
                        if(cmd.equals("SetQuiz")){
                            String msg = inputTokens[1];
                            //クイズラベルの位置と速度を同期
                            int x = Integer.parseInt(inputTokens[3]);
                            int y = Integer.parseInt(inputTokens[4]);
                            int xVelocity = Integer.parseInt(inputTokens[5]);
                            int yVelocity = Integer.parseInt(inputTokens[6]);
                            int length = Integer.parseInt(inputTokens[2]);
                            //クイズの順番を同期
                            for(int i = 0 ; i < length ; i++){ 
                                int num = Character.getNumericValue(msg.charAt(i));
                                Quiz.quizIndexlist[i] = num;
                            }
                            quizlabel.x = x;
                            quizlabel.y = y;
                            quizlabel.xVelocity = xVelocity;
                            quizlabel.yVelocity = yVelocity;
                            quizlabel.setLocation(quizlabel.x,quizlabel.y);

                            //クイズの順番が同期できてから選択肢と問題文を同期
                            choice01Label.setchoice01();
                            choice02Label.setchoice02();
                            choice03Label.setchoice03();
                            quizlabel.setQuizText();
                        }
                        //誰かがボタンに到達したときの処理
                        if(cmd.equals("Touchbutton")){
                            int number =  Integer.parseInt(inputTokens[1]);
                            //ボタンに到達していないプレイヤーは動けなくする（Maze.canmove > 3で動ける）
                            if(Maze.myNumber != number){
                               Maze.canmove = 3; 
                            }
                            Quiz.canAnswer = true;
                            //クイズラベルを一旦停止
                            quizlabel.xVelocity  = 0;
                            quizlabel.yVelocity = 0;
                            //選択肢を見えるようにする
                            choice01Label.setVisible(true);
                            choice02Label.setVisible(true);
                            choice03Label.setVisible(true);
                            selectIconLabel.setVisible(true);
                            //問題文は見えなくする
                            quizlabel.setVisible(false);
                            //音楽は一旦止める
                            Quiz.timerSoundPlayer.stop();
                            theSoundPlayer1 = new SoundPlayer("button.wav");
                            theSoundPlayer1.SetLoop(false);//ＢＧＭとして再生を繰り返す
                            theSoundPlayer1.play(); 
                        }

                        //ボタンを離れたときの処理
                        if(cmd.equals("ReleaseButton")){
                            int number =  Integer.parseInt(inputTokens[1]);
                            //動けなったプレイヤーを動けるようにする
                            if(Maze.myNumber != number){
                               Maze.canmove = 4; 
                            }
                            if(Maze.myNumber == 1){
                                int xVelocity =  new java.util.Random().nextInt(10) ;
                                int yVelocity =  new java.util.Random().nextInt(10) ;
                                String msg = "SetVelocity" + " " + String.valueOf(xVelocity) + " " + String.valueOf(yVelocity);
                                //サーバに情報を送る
                                out.println(msg);//送信データをバッファに書き出す
                                out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
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
                            HitSoundPlayer.SetLoop(false);//ＢＧＭとして再生を繰り返す
                            HitSoundPlayer.play(); 
                        }
                        if(cmd.equals("E_RESTART")){
                            enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);
                            HitSoundPlayer= new SoundPlayer("hit.wav");
                            HitSoundPlayer.SetLoop(false);//ＢＧＭとして再生を繰り返す
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
                            theSoundPlayer1.SetLoop(false);//ＢＧＭとして再生を繰り返す
                            theSoundPlayer1.play();
                            Quiz.timerSoundPlayer.SetLoop(true);
                            Quiz.timerSoundPlayer.play();
                            //正解なら
                            correctLabel.setVisible(true);//正解の文字表示
                            correctLabel.setVisible(false);
                            //正解なら全員の位置を初期位置に戻す
                            playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);

                            //正解者にポイント加算
                            if(number == 1){
                                Quiz.p_point++;
                                p_QuizPointLabel.setText(String.valueOf(Quiz.p_point / 2));
                            }else if(number == 0){
                                Quiz.e_point++;
                                e_QuizPointLabel.setText(String.valueOf(Quiz.e_point / 2));
                            }
                            if(Quiz.p_point == 4){
                                if(Maze.myNumber == 0){
                                    //まけ
                                    quizlabel.xVelocity = 0;
                                    quizlabel.yVelocity = 0;
                                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.LOSER);
                                    theSoundPlayer2 = new SoundPlayer("laughter.wav");
                                    theSoundPlayer2.SetLoop(true);//ＢＧＭとして再生を繰り返す
                                    theSoundPlayer2.play(); 
                                } else if(Maze.myNumber == 1){
                                    //勝ち
                                    quizlabel.xVelocity = 0;
                                    quizlabel.yVelocity = 0;
                                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.WINNER);
                                    theSoundPlayer2 = new SoundPlayer("win.wav");
                                    theSoundPlayer2.SetLoop(false);//ＢＧＭとして再生を繰り返す
                                    theSoundPlayer2.play();
                                }
                            }
                            if(Quiz.e_point == 4){
                                if(Maze.myNumber == 0){
                                    //勝ち
                                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.WINNER);
                                    theSoundPlayer2 = new SoundPlayer("win.wav");
                                    theSoundPlayer2.SetLoop(false);//ＢＧＭとして再生を繰り返す
                                    theSoundPlayer2.play();
                                } else if(Maze.myNumber == 1){
                                    //負け
                                    Main.mainWindow.setFrontScreenAndFocus(ScreenMode.LOSER);
                                    theSoundPlayer2 = new SoundPlayer("laughter.wav");
                                    theSoundPlayer2.SetLoop(true);//ＢＧＭとして再生を繰り返す
                                    theSoundPlayer2.play();
                                }
                            }
                            
                            //クイズ更新
                            Quiz.quizindex ++;
                            quizlabel.setText(Quiz.quiztextlist[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice01Label.setText("1:"  + Quiz.choice01list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice01Label.setBounds(100,1700,(100 * Quiz.choice01list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20); 
                            choice02Label.setText("2:"  + Quiz.choice02list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice02Label.setBounds(1000,1700,(100 * Quiz.choice02list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20);
                            choice03Label.setText("3:"  + Quiz.choice03list[Quiz.quizIndexlist[Quiz.quizindex]]);
                            choice03Label.setBounds(1900,1700,(100 * Quiz.choice03list[Quiz.quizIndexlist[Quiz.quizindex]].length()) + 200,100 + 20);
                            
                            
                        String msg = "ReleaseButton" + " " + Maze.myNumber;
                        //サーバに情報を送る
                        out.println(msg);//送信データをバッファに書き出す
                        out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                            
                        }
                        if(cmd.equals("Huseikai")){
                            int number =  Integer.parseInt(inputTokens[1]);                            
                            //不正解なら
                            theSoundPlayer1 = new SoundPlayer("huseikai.wav");
                            theSoundPlayer1.SetLoop(false);//ＢＧＭとして再生を繰り返す
                            theSoundPlayer1.play();
                            Quiz.timerSoundPlayer.SetLoop(true);
                            Quiz.timerSoundPlayer.play();
                            incorrectLabel.setVisible(false);
                            //不正解なら間違った人だけ初期位置に戻す
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
				System.err.println("エラーが発生しました: " + e);
			}
		}
	}

    public void prepareComponets(){
        /*
        gameLabel = new JLabel();
        gameLabel.setText("ゲーム画面");
        gameLabel.setBounds(100,200,100,30);
        this.add(gameLabel);
        */

        pleasepush = new JLabel();
        pleasepush.setText("９を押してね!");
        pleasepush.setFont(new Font("MV boil",Font.BOLD,100));
        pleasepush.setHorizontalTextPosition(JLabel.CENTER);
        pleasepush.setVerticalTextPosition(JLabel.BOTTOM);
        pleasepush.setOpaque(true);
        pleasepush.setBackground(Color.orange);
        pleasepush.setBounds(1200,1700,1000,100);
        this.add(pleasepush);

        //プレイヤーのラベルの準備
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

        //パスボタンの設定
        /*
        joinButton = new JButton();//ボタンにアイコンを設定する
        setjoinButton();
        */

        //youLabel = new YouLabel();
        //this.add(youLabel);


        
        /*
        Maze = new Maze();
        CreateMaze c = new CreateMaze();
        c.createMaze(Maze.list);
        
        //詰み防止
        Maze.list[19][1] = 0;
        Maze.list[1][18] = 0;

        //ボタンの位置を開けて置く
        Maze.list[Maze.Button_X_Index][Maze.Button_Y_Index] = 0;
        */

        

        /*
        int count = 0;
        //リストで１になっているところに壁を生成
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
        joinButton.setText("参加");
        joinButton.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 100));
        //joinButton.addMouseListener(this);
        //joinButton.repaint();
        this.add(joinButton);
    }

    public void canmovePlus(){
        String msg = "Canmove++"+" "+ Maze.myNumber;
        //サーバに情報を送る
        out.println(msg);//送信データをバッファに書き出す
        out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
    }

    private class MyKeyListener implements KeyListener{
        //貼り付けを保持
        GamePanel panel;

        //コンストラクタ
        MyKeyListener(GamePanel p){
            super();
            panel = p;
            p.addKeyListener(this);
        }
    
        //キーの設定
        @Override
        public void keyTyped(KeyEvent e) {
            String direction_input = "";
            //player側の操作
            if((e.getKeyChar()) == '9'){
                canmovePlus();
                pleasepush.setText("ちょっと待ってね！");
            }
            if(Maze.canmove > 3){
                if(Maze.myNumber == 1){
                    switch(e.getKeyChar()){
                    //上
                    case 'i':
                    case 'I':
                        if(!(isHit(playerlabel.getX(),playerlabel.getY()-Maze.WallSize))){
                            if(playerlabel.getY() > 0 ){
                                direction_input = "up";
                            }
                        }
                        break;
                    //下
                    case 'k':
                    case 'K':
                        if(!(isHit(playerlabel.getX(),playerlabel.getY()+Maze.WallSize))){
                            if(playerlabel.getY() + playerlabel.playerImage.getHeight(null) < panel.getHeight() ){
                                direction_input = "down";
                            }
                        }
                        break;
                    //左
                    case 'j':
                    case 'J':
                        if(!(isHit(playerlabel.getX()-Maze.WallSize,playerlabel.getY()))){
                            if(playerlabel.getX() > 0 ){
                                direction_input = "left";
                            }
                        }
                        break;
                    //右
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
                //enemy側の操作
                else if(Maze.myNumber == 0){
                    switch(e.getKeyChar()){
                    //上
                    case 'i':
                    case 'I':
                        if(!(isHit(enemylabel.getX(),enemylabel.getY()-Maze.WallSize))){
                            if(enemylabel.getY() > 0 ){
                                direction_input = "up";
                            }
                        }
                        break;
                    //下
                    case 'k':
                    case 'K':
                        if(!(isHit(enemylabel.getX(),enemylabel.getY()+Maze.WallSize))){
                            if(enemylabel.getY() + enemylabel.enemyImage.getHeight(null) < panel.getHeight() ){
                                direction_input = "down";
                            }
                        }
                        break;
                    //左
                    case 'j':
                    case 'J':
                        if(!(isHit(enemylabel.getX()-Maze.WallSize,enemylabel.getY()))){
                            if(enemylabel.getX() > 0 ){
                                direction_input = "left";
                            }
                        }
                        break;
                    //右
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
                    //サーバに情報を送る
                    out.println(msg01);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                    /*
                    selectIconLabel.setLocation(300,1500); 
                    SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER01;
                    */
                break;
                case '2':
                    String msg02 = "Select02";
                    //サーバに情報を送る
                    out.println(msg02);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                    /*
                    selectIconLabel.setLocation(1200,1500); 
                    SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER02;
                    */
                break;
                case '3':
                    String msg03 = "Select03";
                    //サーバに情報を送る
                    out.println(msg03);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                    /*
                    selectIconLabel.setLocation(2000,1500); 
                    SelectIconLabel.cheakSelect = SelectIconLabel.Select.ANSWER03; 
                    */
                break;
                case '4':
                    if(Quiz.DoAnswer){
                        int answer = 99;
                        //選んでいる選択肢番号取得
                        if(SelectIconLabel.cheakSelect == SelectIconLabel.Select.ANSWER01){
                            answer = 0;  
                        }else if(SelectIconLabel.cheakSelect == SelectIconLabel.Select.ANSWER02){
                            answer = 1;
                        }else if(SelectIconLabel.cheakSelect == SelectIconLabel.Select.ANSWER03){
                            answer = 2;
                        } else{
                            System.out.println("answererror");
                        }

                        //正誤判定
                        if(Quiz.answerindexlist[Quiz.quizIndexlist[Quiz.quizindex]]== answer){
                            String seikai = "Seikai" + " " + Maze.myNumber;
                            //サーバに情報を送る
                            out.println(seikai);//送信データをバッファに書き出す
                            out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                            /*
                            theSoundPlayer1 = new SoundPlayer("seikai.wav");
                            theSoundPlayer1.SetLoop(false);//ＢＧＭとして再生を繰り返す
                            theSoundPlayer1.play();
                            Quiz.timerSoundPlayer.SetLoop(true);
                            Quiz.timerSoundPlayer.play();
                            //正解なら
                            correctLabel.setVisible(true);//正解の文字表示
                            correctLabel.setVisible(false);
                            //正解なら全員の位置を初期位置に戻す
                            playerlabel.setLocation(Maze.Start_player_X,Maze.Start_player_Y);
                            enemylabel.setLocation(Maze.Start_enemy_X,Maze.Start_enemy_Y);

                            //正解者にポイント加算
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
                            
                            //クイズ更新
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
                            //迷路再構築
                            Maze = new Maze();
                            CreateMaze c = new CreateMaze();
                            c.createMaze(Maze.list);
                            //詰み防止
                            Maze.list[19][1] = 0;
                            Maze.list[1][18] = 0;
                            Maze.list[Maze.Button_X_Index][Maze.Button_Y_Index] = 0;
                            int count = 0;
                            Maze.WallList = new ArrayList<>();
                            //リストで１になっているところに壁を生成
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
                            
                            //詰み防止
                            list[19][1] = 0;
                            list[1][18] = 0;

                            //ボタンの位置を開けて置く
                            list[Maze.Button_X_Index][Maze.Button_Y_Index] = 0;
                            String listmsg="";
                            for(int i = 0 ; i < Maze.Masu ; i++){
                                for(int k = 0; k < Maze.Masu ; k++){
                                    listmsg = listmsg + Integer.toString(list[i][k]);
                                }   
                            }
                            String msg = "CreateMaze"+" "+listmsg;
                            //サーバに情報を送る
                            out.println(msg);//送信データをバッファに書き出す
                            out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                        

                        }else{
                            String huseikai = "Huseikai" + " " + Maze.myNumber;
                            //サーバに情報を送る
                            out.println(huseikai);//送信データをバッファに書き出す
                            out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                            /*
                            /*
                            //不正解なら
                            theSoundPlayer1 = new SoundPlayer("huseikai.wav");
                            theSoundPlayer1.SetLoop(false);//ＢＧＭとして再生を繰り返す
                            theSoundPlayer1.play();
                            Quiz.timerSoundPlayer.SetLoop(true);
                            Quiz.timerSoundPlayer.play();
                            incorrectLabel.setVisible(false);
                            //不正解なら間違った人だけ初期位置に戻す
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
            touchButton();//ラベルがボタンに触れているのかをキー操作ごとに監視
            

            String msg = "MOVE"+" "+ direction_input +" "+Maze.myNumber;
            //サーバに情報を送る
            out.println(msg);//送信データをバッファに書き出す
            out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
        }
        @Override
        public void keyReleased(KeyEvent e) {
            
        }
        @Override
        public void keyPressed(KeyEvent e) {//マウスでオブジェクトを押したときの処理（クリックとの違いに注意）
            
        
        }
       
    }
    
    //壁のグラフィックの設定
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
                    //サーバに情報を送る
                    out.println(msg);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                }
                if(e_x> cat.x  -50 && e_x < cat.x + xLength + 50 && e_y > cat.y -50 && e_y < cat.y + yLength + 50){
                    String msg = "E_RESTART";
                    //サーバに情報を送る
                    out.println(msg);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                }
            }
            
        }

    }

    //（ｘ、ｙ）に壁があるかを感知するメソッド
    private boolean isHit(int x, int y) {
        // (x,y)に壁があったらぶつかる
        if(Maze.myNumber == 1){
            if(playerlabel.getX() > 900 && playerlabel.getY() > 300){
                int i = ((x) - 900) / Maze.WallSize;//横方向に何マス目か
                int k = ((y) - 300) / Maze.WallSize;//縦方向に何マス目か
                if(i <Maze.Masu && k < Maze.Masu){//マス外でもプレイヤーが動けるようにするために入れてる
                    if (Maze.list[i][k] == 1) {//もし（i,k)が１なら
                        return true;//trueを返す
                    }
                }
            }
        }
        else if(Maze.myNumber == 0){
            if(enemylabel.getX() > 900 && enemylabel.getY() > 300){
                int i = ((x) - 900) / Maze.WallSize;//横方向に何マス目か
                int k = ((y) - 300) / Maze.WallSize;//縦方向に何マス目か
                if(i <Maze.Masu && k < Maze.Masu){//マス外でもプレイヤーが動けるようにするために入れてる
                    if (Maze.list[i][k] == 1) {//もし（i,k)が１なら
                        return true;//trueを返す
                    }
                }
            }
        }
        
        
        // なければぶつからないからfalseを返す
        return false;
    }

    //PlayerLabelがbuttonに接触しているかどうかを感知するメソッド
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
                //サーバに情報を送る
                out.println(msg);//送信データをバッファに書き出す
                out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                /*
                Quiz.canAnswer = true; 
                choice01Label.setVisible(true);
                choice02Label.setVisible(true);
                choice03Label.setVisible(true);
                selectIconLabel.setVisible(true);
                quizlabel.setVisible(false);
                Quiz.timerSoundPlayer.stop();
                theSoundPlayer1 = new SoundPlayer("button.wav");
                theSoundPlayer1.SetLoop(false);//ＢＧＭとして再生を繰り返す
                theSoundPlayer1.play();
                */

            }
            else{
                if(Quiz.DoAnswer){
                    String msg = "ReleaseButton"+" "+Maze.myNumber;
                    //サーバに情報を送る
                    out.println(msg);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
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
                //サーバに情報を送る
                out.println(msg);//送信データをバッファに書き出す
                out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
                /*
                Quiz.canAnswer = true; 
                choice01Label.setVisible(true);
                choice02Label.setVisible(true);
                choice03Label.setVisible(true);
                selectIconLabel.setVisible(true);
                quizlabel.setVisible(false);
                Quiz.timerSoundPlayer.stop();
                theSoundPlayer1 = new SoundPlayer("button.wav");
                theSoundPlayer1.SetLoop(false);//ＢＧＭとして再生を繰り返す
                theSoundPlayer1.play();
                */
            }
            else{
                if(Quiz.DoAnswer){
                    String msg = "ReleaseButton"+" "+Maze.myNumber;
                    //サーバに情報を送る
                    out.println(msg);//送信データをバッファに書き出す
                    out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
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