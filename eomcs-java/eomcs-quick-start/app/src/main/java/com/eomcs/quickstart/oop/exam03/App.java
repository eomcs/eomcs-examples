package com.eomcs.quickstart.oop.exam03;

import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.print("국어 점수? ");
    Score.kor = sc.nextInt();

    System.out.print("영어 점수? ");
    Score.eng = sc.nextInt();

    System.out.print("수학 점수? ");
    Score.math = sc.nextInt();

    sc.close();

    System.out.printf("총점: %d%n", Score.sum());
    System.out.printf("평균: %.1f%n", Score.aver());
  }
}
