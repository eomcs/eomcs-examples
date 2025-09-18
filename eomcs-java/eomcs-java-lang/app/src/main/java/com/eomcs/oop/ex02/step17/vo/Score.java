package com.eomcs.oop.ex02.step17.vo;

// 다른 패키지에서도 이 클래스를 사용할 수 있도록 하려면
// public 으로 공개해야 한다.
public class Score {

  // 다른 패키지에서 이 설계도에 따라 만든 변수에 접근할 수 있도록 하려면
  // 접근 범위를 public 으로 공개해야 한다.
  public String name;
  public int kor;
  public int eng;
  public int math;
  public int sum;
  public float aver;

  // 다른 패키지에서 메서드를 사용할 수 있도록 하려면 public 으로 공개해야 한다.
  public void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }
}
