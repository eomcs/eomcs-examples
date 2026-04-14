package com.eomcs.quickstart.oop.exam02;

public class App4 {

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
    kor = Integer.parseInt(args[0]);
    eng = Integer.parseInt(args[1]);
    math = Integer.parseInt(args[2]);

    System.out.printf("총점: %d%n", sum());
    System.out.printf("평균: %.1f%n", aver());
  }
}
