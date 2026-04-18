package com.eomcs.advanced.oop.exam05;

import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    Score score = new Score();

    System.out.print("국어 점수? ");
    score.kor = sc.nextInt();

    System.out.print("영어 점수? ");
    score.eng = sc.nextInt();

    System.out.print("수학 점수? ");
    score.math = sc.nextInt();

    sc.close();

    System.out.printf("총점: %d%n", score.sum());
    System.out.printf("평균: %.1f%n", score.aver());
  }
}
