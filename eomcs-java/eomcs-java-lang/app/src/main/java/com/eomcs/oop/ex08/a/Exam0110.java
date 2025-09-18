//# 캡슐화 문법 사용 전 - 개발자가 클래스를 작성한 사람의 의도대로 정상적으로 사용할 때
package com.eomcs.oop.ex08.a;

class Score {
  String name;
  int kor;
  int eng;
  int math;
  int sum;
  float aver;

  void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }
}

public class Exam0110 {
  public static void main(String[] args) {

    // 1) 개발자가 새로 정의한 데이터 타입에 따라 변수를 준비한다.
    Score s1 = new Score();

    // 2) 레퍼런스에 저장된 인스턴스 주소로 찾아가서 
    //    각 변수의 값을 설정한다.
    s1.name = "홍길동";
    s1.kor = 100;
    s1.eng = 90;
    s1.math = 80;

    // 3) compute() 연산자를 사용하여 새 데이터 타입의 값을 다룬다.
    //    값.연산자();   ===> 예) i++;
    // - compute()는 Score 설계도에 따라 만든 변수의 값을 다룬다.
    //   즉 변수의 값을 다루는 연산자 역할을 수행한다.
    // - 그래서 인스턴스 메서드를 "연산자(operator)"라 부른다.
    s1.compute();

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
        s1.name, s1.kor, s1.eng, s1.math, s1.sum, s1.aver);

  }
}











