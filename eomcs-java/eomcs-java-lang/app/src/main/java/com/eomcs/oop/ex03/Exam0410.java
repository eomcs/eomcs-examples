// 특별한 인스턴스 메서드 - 생성자(constructor)
package com.eomcs.oop.ex03;

public class Exam0410 {

  // Exam0410 클래스 조차도 기본 생성자가 자동으로 추가된다.
  // 컴파일러가 자동으로 다음 생성자를 붙일 것이다.
  //public Exam0410() {}

  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float average;

    // 생성자
    // => 클래스 이름과 같은 이름으로 메서드를 만든다.
    // => 리턴 타입을 선언하지 않는다. 즉 값을 리턴하지 않는다.
    // => 인스턴스를 만들 때 자동 호출된다.
    //    일반 메서드처럼 나중에 따로 호출할 수 없다.
    // => 만약 개발자가 생성자를 만들지 않으면 컴파일러가 기본 생성자를 자동으로 추가한다.
    //    따라서 모든 클래스는 반드시 한 개 이상의 생성자가 있다.
    // => 보통 인스턴스를 만든 후에 인스턴스 변수를 초기화시키는 용도로 정의한다.
    // => 생성된 인스턴스가 제대로 쓰일 수 있도록 유효한 값으로 초기화시키는 일을 한다.
    // => 다음과 파라미터가 없는 생성자가 기본 생성자이다.(default constructor)
    Score() {
      System.out.println("Score()");
    }


    public void compute() {
      this.sum = this.kor + this.eng + this.math;
      this.average = this.sum / 3f;
    }
  }

  public static void main(String[] args) {
    // 생성자를 호출하는 방법
    // => 인스턴스를 생성하는 new 명령에서 호출할 생성자의 파라미터 값을 지정하면 된다.
    // => 다음과 같이 파라미터 값을 주지 않으면 기본 생성자를 호출하라는 뜻이 된다.
    Score s1 = new Score(); // 드디어 클래스 명 다음에 오는 괄호()의 의미를 알게 되었다.

    // 생성자를 호출하지 않을 수 있는가?
    // => 컴파일 오류!
    //    Score s2 = new Score;

    Score s3 = new Score();

    //    s3.Score(); // 생성자는 따로 호출할 수 없다!
  }
}








