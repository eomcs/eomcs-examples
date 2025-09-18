// local class : 스태틱 메서드와 로컬 클래스
package com.eomcs.oop.ex11.d;

class C2 {
  int v1 = 1;

  static void m1() {
    // 스태틱 메서드는 C2 인스턴스 주소를 저장할 this라는 변수가 없다.

    class X {
      // 그래서 바깥 클래스의 인스턴스를 사용할 수 없다.
      // .class 파일을 확인해보면 바깥 클래스의 인스턴스를 보관할 필드가 선언되어 있지 않다.
      // 또한 생성자에도 바깥 클래스의 인스턴스를 받는 파라미터가 선언되어 있지 않다.
      //
      //      class com.eomcs.oop.ex11.d.C2$1X {
      //
      //        C2$1X();
      //          0  aload_0 [this]
      //          1  invokespecial java.lang.Object() [8]
      //
      void f() {
        //        System.out.printf("v1 = %d\n", C2.this.v1); // 컴파일 오류!
      }
    }
    X obj = new X(); // 컴파일 해도 같다. ===> new X();
    obj.f();
  }
}

public class Exam0220 {

  public static void main(String[] args) {
    C2 c = new C2();
    c.m1();
  }

}
