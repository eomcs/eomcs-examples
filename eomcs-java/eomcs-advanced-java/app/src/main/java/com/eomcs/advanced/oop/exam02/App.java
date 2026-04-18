package com.eomcs.advanced.oop.exam02;

public class App {

  public static void main(String[] args) {
    int kor = 100;
    int eng = 90;
    int math = 80;

    int sum = kor + eng + math;
    float aver = (float) sum / 3;

    System.out.printf("총점: %d%n", sum);
    System.out.printf("평균: %.1f%n", aver);
  }
}
