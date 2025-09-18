// static nested class : 선언할 수 있는 멤버
package com.eomcs.oop.ex11.b;

class A2 {

  static class X {
    // top level class 처럼 스태틱 멤버 선언 가능
    static int v1;
    static void m1() {}
    static {}

    // top level class 처럼 인스턴스 멤버 선언 가능
    int v2;
    void m2() {}
    {}
  }

}

public class Exam0111 {

  public static void main(String[] args) {
    // 레퍼런스 선언
    A2.X obj;

    // 인스턴스 생성
    obj = new A2.X();
  }

}
