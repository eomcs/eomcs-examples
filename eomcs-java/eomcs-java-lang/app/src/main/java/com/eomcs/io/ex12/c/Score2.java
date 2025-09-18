package com.eomcs.io.ex12.c;

import java.io.Serializable;

public class Score2 implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String name;
  private int kor;
  private int eng;
  private int math;
  private int sum;
  private float aver;

  public Score2() {
    System.out.println("Score2()");
  }

  @Override
  public String toString() {
    return "Score2 [name=" + name + ", kor=" + kor + ", eng=" + eng + ", math=" + math + ", sum="
        + sum + ", aver=" + aver + "]";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    System.out.println("setName()");
    this.name = name;
  }

  public int getKor() {
    return kor;
  }

  public void setKor(int kor) {
    System.out.println("setKor()");
    this.kor = kor;
  }

  public int getEng() {
    return eng;
  }

  public void setEng(int eng) {
    System.out.println("setEng()");
    this.eng = eng;
  }

  public int getMath() {
    return math;
  }

  public void setMath(int math) {
    System.out.println("setMath()");
    this.math = math;
  }

  public int getSum() {
    return sum;
  }

  public void setSum(int sum) {
    System.out.println("setSum()");
    this.sum = sum;
  }

  public float getAver() {
    return aver;
  }

  public void setAver(float aver) {
    System.out.println("setAver()");
    this.aver = aver;
  }
  
  
}




