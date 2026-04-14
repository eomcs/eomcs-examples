package com.eomcs.quickstart.oop.exam02;

import java.util.Scanner;

public class App3 {

  static int kor;
  static int eng;
  static int math;

  static int sum() {
    return kor + eng + math;
  }

  static float aver() {
    return (float) sum() / 3;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.print("국어 점수? ");
    kor = sc.nextInt();

    System.out.print("영어 점수? ");
    eng = sc.nextInt();

    System.out.print("수학 점수? ");
    math = sc.nextInt();

    sc.close();

    System.out.printf("총점: %d%n", sum());
    System.out.printf("평균: %.1f%n", aver());
  }
}
