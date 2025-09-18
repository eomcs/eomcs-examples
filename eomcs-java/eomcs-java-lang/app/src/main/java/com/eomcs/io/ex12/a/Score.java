package com.eomcs.io.ex12.a;

import java.io.Serializable;

// ObjectOutputStream/ObjectInputStream을 통해 인스턴스의 값을 
// 바이트 배열로 자동으로 변환하여 출력하고 읽으려면 
// java.io.Serializable 인터페이스를 구현해야 한다.
// => Serializable 인터페이스는 메서드가 선언되어 있지 않다.
//    단지 해당 클래스의 인스턴스가 바이트 배열로 자동으로 출력될 수 있는지 
//    허락하는 용으로 사용한다.
// => 즉 Serializable 인터페이스를 구현한 클래스는 
//    ObjectOutputStream이 인스턴스를 바로 출력할 수 있다.
// 
public class Score implements Serializable {
  private String name;
  private int kor;
  private int eng;
  private int math;
  private int sum;
  private float aver;
  
  public Score() {
    System.out.println("Score()");
  }
  
  public Score(String name, int kor, int eng, int math) {
    this.name = name;
    this.kor = kor;
    this.eng = eng;
    this.math = math;
    compute();
  }

  @Override
  public String toString() {
    return "Score [name=" + name + ", kor=" + kor + ", eng=" + eng + ", math=" + math + ", sum="
        + sum + ", aver=" + aver + "]";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getKor() {
    return kor;
  }

  public void setKor(int kor) {
    this.kor = kor;
    this.compute();
  }

  public int getEng() {
    return eng;
  }

  public void setEng(int eng) {
    this.eng = eng;
    this.compute();
  }

  public int getMath() {
    return math;
  }

  public void setMath(int math) {
    this.math = math;
    this.compute();
  }

  public int getSum() {
    return sum;
  }

  public float getAver() {
    return aver;
  }
  
  public void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }
}




