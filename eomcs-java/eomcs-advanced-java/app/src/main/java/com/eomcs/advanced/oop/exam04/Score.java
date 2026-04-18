package com.eomcs.advanced.oop.exam04;

public class Score {

  int kor;
  int eng;
  int math;

  static int sum(Score s) {
    return s.kor + s.eng + s.math;
  }

  static float aver(Score s) {
    return (float) sum(s) / 3;
  }
}
