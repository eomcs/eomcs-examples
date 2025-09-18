// static nested class : 다른 멤버가 중첩 클래스 사용하기
package com.eomcs.oop.ex11.b;

class C {
  static void m1() {
    // 같은 스태틱 멤버는 사용 가능!
    X obj = new X();
    obj.test();
  }

  void m2() {
    // 인스턴스 멤버는 스태틱 멤버 사용 가능!
    m1(); // OK!

    X obj = new X();
    obj.test();
  }

  static class X {
    void test() {
      System.out.println("X.test()");
    }
  }
}

public class Exam0310 {

  public static void main(String[] args) {
    C.m1();

    C outer = new C();
    outer.m2();
  }

}