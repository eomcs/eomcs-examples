package com.eomcs.oop.ex02.step03;

public class Calculator {

  //## 클래스 변수 사용
  // - 메서드들의 작업 결과를 보관할 때 사용할 변수이다.
  // - 변수 선언에 static을 붙이다.
  // - "스태틱 변수"라고도 부른다.
  // - 클래스 변수는 new 명령으로 생성하지 않는다.
  // - 클래스가 메모리에 로딩될 때 자동으로 "Method Area" 영역에 생성된다.
  static int result = 0;

  //메서드 작업 결과는 클래스 변수에 보관한다.
  static void plus(int value) {
    //result = result + value;
    result += value;
  }

  static void minus(int value) {
    result -= value;
  }

  static void multiple(int value) {
    result *= value;
  }

  static void divide(int value) {
    result /= value;
  }

  // 다음 메서드는 계산 결과를 사용하지 않는다.
  // 파라미터 값에 대해 절대값을 리턴하는 일을 한다.
  static int abs(int a) {
    return a >= 0 ? a : a * -1;
  }
}
