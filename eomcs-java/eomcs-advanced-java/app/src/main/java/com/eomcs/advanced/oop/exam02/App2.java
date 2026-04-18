package com.eomcs.advanced.oop.exam02;

public class App2 {

  static int kor = 100;
  static int eng = 90;
  static int math = 80;

  static int sum() {
    return kor + eng + math;
  }

  static float aver() {
    return (float) sum() / 3;
  }

  public static void main(String[] args) {
    System.out.printf("총점: %d%n", sum());
    System.out.printf("평균: %.1f%n", aver());
  }
}
