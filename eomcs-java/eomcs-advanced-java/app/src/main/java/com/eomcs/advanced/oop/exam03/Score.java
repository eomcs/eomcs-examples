package com.eomcs.advanced.oop.exam03;

public class Score {

  static int kor;
  static int eng;
  static int math;

  static int sum() {
    return kor + eng + math;
  }

  static float aver() {
    return (float) sum() / 3;
  }
}
