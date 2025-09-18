package com.eomcs.oop.ex02.step06;

public class Calculator {

  int result = 0;

  //인스턴스 변수를 다룰 때는 인스턴스 메서드를 사용하는 것이 편하다!
  // 왜?
  // - 인스턴스 주소를 파라미터로 받을 필요가 없기 때문이다.
  // - 메서드를 호출할 때 앞쪽에 인스턴스 주소를 지정한다.
  //
  void plus(int value) {
    // 메서드를 호출할 때 앞에서 전달한 인스턴스는
    // this 라는 이름의 내장 변수에 자동 저장된다.
    this.result += value;
  }

  void minus(int value) {
    this.result -= value;
  }

  void multiple(int value) {
    this.result *= value;
  }

  void divide(int value) {
    this.result /= value;
  }

  // 다음 메서드는 result 변수를 사용하지 않기 때문에
  // Calculator의 인스턴스 주소를 받을 필요가 없다. 
  static int abs(int a) {
    // 스태틱 메서드는 this라는 내장 변수가 없다.
    return a >= 0 ? a : a * -1;
  }
}






