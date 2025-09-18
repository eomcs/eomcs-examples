// 오버라이딩(overriding) - this/super의 사용
package com.eomcs.oop.ex06.c;

// this.메서드() 호출?
// => 현재 클래스(this의 실제 타입)부터 호출할 메서드를 찾아 올라 간다.
//
// super.메서드() 호출?
// => 부모 클래스(메서드가 소속된 클래스의 부모)부터 호출할 메서드를 찾아 올라 간다.
//

public class Exam0420 {
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

    void test() {
      this.m1(); // X4의 m1() : this가 실제 가리키는 인스턴스 클래스를 기준으로 메서드를 찾아 올라 간다.
      super.m1(); // X2의 m1() : test()가 소속된 클래스를 기준으로 수퍼 클래스부터 메서드를 찾아 올라간다.

      this.m2(); // X3의 m2()
      super.m2(); // X3의 m2()

      //      super.super.m1(); // 컴파일 오류! 이런 문법은 없다! 무협지 문법!

    }
  }

  public static void main(String[] args) {
    X4 obj = new X4();
    obj.test();

    // 레퍼런스에서 super를 사용할 수 없다.
    //    obj.super.m1(); // 컴파일 오류!
  }
}








