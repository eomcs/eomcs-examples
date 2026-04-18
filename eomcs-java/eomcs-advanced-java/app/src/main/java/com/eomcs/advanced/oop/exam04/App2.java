package com.eomcs.advanced.oop.exam04;

import java.util.Scanner;

public class App2 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    Score score1 = new Score();
    Score score2 = new Score();
    Score score3 = new Score();

    System.out.println("[1번 학생]");
    System.out.print("국어 점수? ");
    score1.kor = sc.nextInt();
    System.out.print("영어 점수? ");
    score1.eng = sc.nextInt();
    System.out.print("수학 점수? ");
    score1.math = sc.nextInt();

    System.out.println("[2번 학생]");
    System.out.print("국어 점수? ");
    score2.kor = sc.nextInt();
    System.out.print("영어 점수? ");
    score2.eng = sc.nextInt();
    System.out.print("수학 점수? ");
    score2.math = sc.nextInt();

    System.out.println("[3번 학생]");
    System.out.print("국어 점수? ");
    score3.kor = sc.nextInt();
    System.out.print("영어 점수? ");
    score3.eng = sc.nextInt();
    System.out.print("수학 점수? ");
    score3.math = sc.nextInt();

    sc.close();

    System.out.println("----------------------------");
    System.out.printf("1번 학생 => 총점: %d, 평균: %.1f%n", Score.sum(score1), Score.aver(score1));
    System.out.printf("2번 학생 => 총점: %d, 평균: %.1f%n", Score.sum(score2), Score.aver(score2));
    System.out.printf("3번 학생 => 총점: %d, 평균: %.1f%n", Score.sum(score3), Score.aver(score3));
  }
}
