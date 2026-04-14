package com.eomcs.quickstart.oop.exam05;

public class Score {

  int kor;
  int eng;
  int math;

  int sum() {
    return this.kor + this.eng + this.math;
  }

  float aver() {
    return (float) this.sum() / 3;
  }
}
