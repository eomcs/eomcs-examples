package com.eomcs.io.ex12.b;

import java.io.Serializable;

// java.io.Serializable
// => serialize를 허락하고 싶다면 이 인터페이스를 구현한다.
// => 자바는 기본적으로 보안을 위해서 자동으로 serialize 하는 것을 허락하지 않는다.
// => 개발자가 직접 이 인터페이스를 붙인 클래스에 대해서만 serialize 할 수 있다.
// => 따라서 이 인터페이스는 serialize 기능을 활성화시키는 표시자로서 역할을 한다.
//    그래서 이 인터페이스에는 메서드가 선언되어 있지 않다.
//    이 인터페이스를 구현하는 클래스는 따로 메서드를 구현할 필요가 없다.
//
public class Score2 implements Serializable {

  private String name;
  //  private String tel; // Test2_1을 실행한 후 이 필드를 추가하라!
  private int kor;
  private int eng;
  private int math;
  private int sum;
  private float aver;

  public Score2() {
    System.out.println("Score2()");
  }

  public Score2(String name, int kor, int eng, int math) {
    this.name = name;
    this.kor = kor;
    this.eng = eng;
    this.math = math;
    compute();
  }

  @Override
  public String toString() {
    return "Score2 [name=" + name + 
        //        ", tel=" + tel + 
        ", kor=" + kor + 
        ", eng=" + eng + 
        ", math=" + math + 
        ", sum=" + sum + 
        ", aver=" + aver + "]";
  }

  //  public String getTel() {
  //    return tel;
  //  }
  //
  //  public void setTel(String tel) {
  //    this.tel = tel;
  //  }

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




