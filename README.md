# WishToAnswerQuiz_java
迷路とクイズを組み合わせた新感覚ゲームです！
迷路を解きながら、クイズを考える忙しいゲームになっています

# DEMO
https://github.com/Taiki-sub/WishToAnswerQuiz_java/assets/136961174/8f0058ec-71d2-4411-9440-6dbba739c372

**タイトル画面**

![スクリーンショット 2023-06-26 002044](https://github.com/Taiki-sub/WishToAnswerQuiz_java/assets/136961174/6389c7fe-8167-4bf0-9f60-227b12a572fa)

**遊び方画面**
![スクリーンショット 2023-06-26 002104](https://github.com/Taiki-sub/WishToAnswerQuiz_java/assets/136961174/c2a5101b-412b-4312-abce-cedd64de413a)

**ゲーム画面**
![スクリーンショット 2023-06-26 002127](https://github.com/Taiki-sub/WishToAnswerQuiz_java/assets/136961174/55aa6b76-a1e5-4d86-a142-95432330f1c4)



# Rule
迷路を解きながら、クイズを考えるゲームです

I,J,K,Lキーを押すことで自分のアイコンを動かします

迷路の真ん中に解答ボタンに到達すると解答権を得ることができます

クイズに間違えるとふりだしに戻ります

クイズバーに当たってもふりだしに戻ります

先に２ポイントとった人が勝利です

# Features

**主な機能**
1. 迷路自動生成機能
   - 「穴掘り法」という迷路生成アルゴリズムを利用して、毎回プレイするたびに迷路の盤面が変わります。
2. クイズバーにぶつかると振り出しに戻る
   - 画面内で動き回っているクイズバーにぶつかると初期位置に戻ります。
3.同期機能 
   - 迷路の盤面、クイズの問題、クイズバーの位置などが同期されている。
     
# Usage
netprog.cmdで以下を入力してください（別々に起動させて、入力してください）
```
javac MyServer2.java
java MyServer2
```
```
javac Main.java
java Main
```
```
javac Main.java
java Main
```
サーバーにアクセスする順番は偶数→奇数になるようにしてください

二人ともゲーム画面になると９キーを押してください。そうするとゲームが開始されます。

９キーを一人のプレイヤーが複数回押すと同期ができないエラーが起こるので、各プレイヤー一回ずつだけ押すようにしてください。
