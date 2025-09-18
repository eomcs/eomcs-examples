// Singleton 적용 전
package com.eomcs.design_pattern.singleton;

class Car1 {
  String model;
  int cc;

  // 생성자를 정의하지 않으면 다음의 기본 생성자가 자동으로 추가된다.
  //public Car1() {}
}

public class Test01 {
  public static void main(String[] args) {
    // 인스턴스 생성
    // => 인스턴스를 여러 개 생성할 수 있다.
    Car1 c1 = new Car1();

    Car1 c2 = new Car1();

    System.out.println(c1 == c2);
  }
}








