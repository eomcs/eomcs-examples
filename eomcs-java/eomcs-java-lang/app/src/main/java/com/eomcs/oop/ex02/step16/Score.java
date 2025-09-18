package com.eomcs.oop.ex02.step16;

public class Score {

  String name;
  int kor;
  int eng;
  int math;
  int sum;
  float aver;

  // static method를 instance method로 바꾸는 이유:
  // - 클래스 메서드로 연산자를 정의하면,
  //   0계산을 수행할 때마다 인스턴스의 주소를 파라미터로 받아야 한다.
  //   매우 번거롭다.
  //
  // static void compute(Score score) {
  //   score.sum = score.kor + score.eng + score.math;
  //   score.average = score.sum / 3f;
  // }
  // - 그래서 자바는 "인스턴스 메서드"라는 것을 제공한다.
  //

  // 인스턴스 메서드 = non-static 메서드
  // - 메서드를 호출할 때 인스턴스의 주소를 파라미터로 넘기지 않는다.
  // - 메서드를 호출할 때(연산자를 사용할 때), 메서드 앞에 인스턴스 주소를 적는다.
  // - 이렇게 전달된 인스턴스 주소는 메서드에 내장된 this라는 변수에 저장된다.
  // - 그래서 파라미터 대신 this를 사용하면 된다.
  // - 인스턴스 메서드는 static을 붙이지 않는다.
  //
  void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }
}
