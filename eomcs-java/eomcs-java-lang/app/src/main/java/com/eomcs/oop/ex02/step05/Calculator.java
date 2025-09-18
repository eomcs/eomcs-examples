package com.eomcs.oop.ex02.step05;

public class Calculator {

  //인스턴스 변수(= non-static 변수)
  // - 작업 결과를 개별적으로 관리하고 싶을 때 인스턴스 변수로 선언한다.
  // - 인스턴스 변수는 클래스가 로딩 될 때 만들어지지 않는다.
  // - new 명령을 사용해서 만들어야 한다.
  // - 변수 선언 앞에 static이 붙지 않는다.
  int result = 0;

  // 인스턴스 변수를 다루는 메서드는 작업을 수행할 때 그 인스턴스 주소를 받아야 한다.
  // result 는 더이상 클래스 변수가 아니기 때문에 직접 접근할 수 없다.
  // 오직 인스턴스 주소를 통해서만 접근 할 수 있다.
  static void plus(Calculator instance, int value) {
    instance.result += value;
  }

  static void minus(Calculator instance, int value) {
    instance.result -= value;
  }

  static void multiple(Calculator instance, int value) {
    instance.result *= value;
  }

  static void divide(Calculator instance, int value) {
    instance.result /= value;
  }

  // 다음 메서드는 result 변수를 사용하지 않기 때문에
  // Calculator의 인스턴스 주소를 받을 필요가 없다. 
  static int abs(int a) {
    return a >= 0 ? a : a * -1;
  }
}






