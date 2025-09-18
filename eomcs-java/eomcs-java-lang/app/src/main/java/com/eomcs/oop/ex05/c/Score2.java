// 기존 코드를 복사하여 새 클래스를 만든 후에 코드를 추가했다.
package com.eomcs.oop.ex05.c;

public class Score2 {

  public String name;
  public int kor;
  public int eng;
  public int math;

  public int sum;
  public float aver;

  // 새 필드를 추가한다.
  public int music;
  public int art;


  // 기존 코드를 변경한다.
  public void compute() {
    this.sum = this.kor + this.eng + this.math + this.music + this.art;
    this.aver = this.sum / 5f;
  }
}







