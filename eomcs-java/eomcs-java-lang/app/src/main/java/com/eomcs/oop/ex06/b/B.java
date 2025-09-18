package com.eomcs.oop.ex06.b;

public class B extends A {
  
  // 부모 클래스인 A에 이미 m() 이라는 이름을 가진 메서드가 여러 개 있다.
  // 그럼에도 불구하고 파라미터 형식이 다른 메서드를 추가한다면 
  // 이것도 마찬가지로 "오버로딩"이다.
  // 즉 한 클래스 안에서 같은 이름의 메서드를 여러 개 만드는 것만 오버로딩이 아니다.
  // 수퍼 클래스에 있는 메서드와 같은 이름을 가진 메서드를 추가해도 오버로딩이다.
  //
  static void m(int a, int b, int c) {
    System.out.println("m(int,int,int)");
  }
}
