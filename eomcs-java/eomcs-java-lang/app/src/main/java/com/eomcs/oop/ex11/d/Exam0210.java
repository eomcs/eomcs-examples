// local class : 인스턴스 메서드와 로컬 클래스
package com.eomcs.oop.ex11.d;

class C {
  int v1 = 1;

  void m1() {
    // 인스턴스 메서드는 C 인스턴스 주소를 this 변수에 저장한다.

    class X {
      // 그래서 인스턴스 메서드 안에 정의된 로컬 클래스는
      // 바깥 클래스의 인스턴스를 사용할 수 있다.
      // .class 파일을 확인해보면 바깥 클래스이 인스턴스 주소를 저장하는 필드가 선언되어 있다.
      // 또한 생성자에도 바깥 클래스의 인스턴스 주소를 받는 파라미터가 선언되어 있다.
      //
      //      class com.eomcs.oop.ex11.d.C$1X {
      //        final synthetic com.eomcs.oop.ex11.d.C this$0;
      //
      //        C$1X(com.eomcs.oop.ex11.d.C arg0);
      //           0  aload_0 [this]
      //           1  aload_1 [arg0]
      //           2  putfield com.eomcs.oop.ex11.d.C$1X.this$0 : com.eomcs.oop.ex11.d.C [10]
      //
      void f() {
        System.out.printf("v1 = %d\n", C.this.v1);
      }
    }
    X obj = new X(); // 컴파일 ==> new X(this); 
    obj.f();
  }
}

public class Exam0210 {

  public static void main(String[] args) {
    C c = new C();
    c.m1();
  }

}
