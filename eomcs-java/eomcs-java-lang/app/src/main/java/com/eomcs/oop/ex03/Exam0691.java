// 스태틱 초기화 블록(static initializer) - 활용 II : 클래스 로딩과 스태틱 블록 실행
package com.eomcs.oop.ex03;

public class Exam0691 {
  static class A {
    static int a = 7;
    static {
      System.out.println("A.static{}");
      a += B.b;
    }
  }
  static class B {
    static int b = 22;
    static {
      System.out.println("B.static{}");
      b += A.a;
    }
  }
  public static void main(String[] args) {
    System.out.println(A.a); // ?
    System.out.println(B.b); // ?

    // 클래스 로딩 절차
    // 1) 클래스를 Method Area에 로딩한다.
    // 2) 스태틱 변수를 만든다.
    // 3) 스태틱 블록을 실행한다.
    //

    // 클래스 로딩
    // => 클래스 멤버(변수, 메서드)를 사용할 때
    // => Class.forName("클래스명") 을 통해 임의로 로딩할 때
    // => 단 한 번 로딩된 클래스는 다시 로딩하지 않는다.
    //

    // 스태틱 블록의 목적
    // => 클래스 멤버(스태틱 변수, 스태틱 메서드)를 사용하기 전에 유효한 값으로 초기화 시키는 것이다.
  }
}


