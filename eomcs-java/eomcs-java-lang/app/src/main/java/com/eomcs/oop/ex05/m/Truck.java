package com.eomcs.oop.ex05.m;

// 여러 클래스에 공통으로 들어 가는 기능이나 필드가 있다면,
// 1) 유지보수가 쉽도록 별도의 클래스로 추출한다.
//    - Sedan과 Truck 사이에 공통 필드와 메서드가 있다.
//    - 공통 기능을 추출하여 별도의 클래스를 정의하는 것을 "일반화(generalization)"라 한다.
// 2) 그리고 상속 관계를 맺는다.
//    - 수퍼 클래스를 추출한 후 수퍼 클래스를 상속 받으면 된다.
//
public class Truck extends Car {
  public void run() {
    System.out.println("덜컹 덜컹 달린다.");
  }

  public void dump() {
    System.out.println("짐을 내린다.");
  }
}
