// Singleton 패턴 적용 후
package com.eomcs.oop.ex04.singleton;

public class Car2 {
  // Car2 객체를 생성하면 다음 스태틱 변수에 보관해둔다.
  private static Car2 instance;

  // 외부에서 생성자를 호출하지 못하도록 private으로 접근을 제한한다.
  private Car2() {
    System.out.println("Car2()");
  }

  public static Car2 getInstance() {
    if (Car2.instance == null) {
      Car2.instance = new Car2();
    }
    return Car2.instance;
  }
}





