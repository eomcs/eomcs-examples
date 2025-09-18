// 다형적 변수와 오버라이딩 - 레퍼런스와 메서드 호출
package com.eomcs.oop.ex06.d;


abstract class Car {
  public abstract void run();
  public void m() {}
}

class Sedan extends Car {
  @Override
  public void run() {
    System.out.println("Sedan.run() 호출됨!");
  }
}

public class Exam0210 {
  public static void main(String[] args) {
    // 1) 다형적 변수의 사용법에 따라,
    //    - super 클래스 레퍼런스로 하위 클래스의 인스턴스를 가리킨다.
    Car car = new Sedan();

    // 2) 오버라이딩 메서드 호출 규칙에 따라,
    //    - 레퍼런스가 실제 가리키는 객체의 클래스부터 메서드를 찾아 올라간다.
    car.run();

  }
}








