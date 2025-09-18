// 오버라이딩(overriding) - 오버라이딩 실수를 방지하는 방법
package com.eomcs.oop.ex06.c;

public class Exam0220 {

  static class B {
    void m(int a) {
      System.out.println("B의 m()");
    }
  }

  static class B3 extends B {
    // 오버라이딩 문법 검사
    // => 오버라이딩을 제대로 했는지 컴파일러에게 검사하도록 요청할 수 있다.
    // => 어떻게?
    //    메서드 정의 앞에 @Override를 붙여라
    @Override // <= 컴파일러야, 내가 상속받은 메서드를 재정의한다고 했는데, 혹시 실수는 없는지 검사해 줄래?
    void m(int x) {
      // 오버라이딩을 한다고 하면서 파라미터의 타입이나 개수, 순서를 달리해서
      // 오버로딩이 되는 경우가 있기 때문에
      // 이를 방지하기 위해 오버라이딩을 하는 메서드 앞에
      // @Override 애노테이션을 붙임으로써 잘못 사용하는 경우를 방지할 수 있다.
      //
      System.out.println("B3의 m()");
    }
  }

  public static void main(String[] args) {
    // 2) @Override를 이용해 오버라이딩의 실수를 방지한 예:
    B3 obj = new B3();
    obj.m(100); // B3의 m(int) 호출

    // B3 클래스에서는 m() 메서드를 제대로 오버라이딩 하였다.
    // @Override ?
    // => 애노테이션 문법이다.
    // => 컴파일러나 JVM에게 전달하는 특별한 주석이다.
    // => 개발자도 자신의 애노테이션을 정의하고 사용할 수 있다.
    //    나중에 따로 배운다!
  }
}








