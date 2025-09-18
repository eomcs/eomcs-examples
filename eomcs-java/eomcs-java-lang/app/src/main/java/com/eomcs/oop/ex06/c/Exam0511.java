// 오버라이딩(overriding) - 레퍼런스가 가리키는 메서드
package com.eomcs.oop.ex06.c;

public class Exam0511 {

  static class X {
    void m1() {
      System.out.println("X의 m1()");
    }
    void m2() {
      System.out.println("X의 m2()");
    }
  }


  static class X2 extends X {
    @Override
    void m1() {
      System.out.println("X2의 m1()");
    }
  }


  static class X3 extends X2 {
    @Override
    void m2() {
      System.out.println("X3의 m2()");
    }
  }


  static class X4 extends X3 {
    @Override
    void m1() {
      System.out.println("X4의 m1()");
    }

    void x() {
      System.out.println("X4의 x()");
    }
  }

  public static void main(String[] args) {
    X4 x4 = new X4();
    x4.m1();   // X4.m1()

    // 인스턴스 필드와 달리 메서드의 경우는
    // 레퍼런스에 대한 형변환에 상관없이
    // 실제 레퍼런스가 가리키는 클래스에서 메서드를 찾아 올라간다.
    //
    ((X3)x4).m1();   // X4.m1()
    ((X3)x4).m2();   // X3.m2()
    //    ((X3)x4).x();   // 컴파일 오류!

    ((X2)x4).m1();   // X4.m1()
    ((X)x4).m1();    // X4.m1();

    X3 x3 = x4;
    X2 x2 = x4;
    X x = x4;

    x3.m1();   // X4.m1()
    x2.m1();   // X4.m1()
    x.m1();   // X4.m1()
  }
}








