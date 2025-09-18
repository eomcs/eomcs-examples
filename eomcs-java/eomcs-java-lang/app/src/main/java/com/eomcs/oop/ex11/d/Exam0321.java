// local class에서 바깥 메서드의 로컬 변수 접근 I
package com.eomcs.oop.ex11.d;

class D2 {
  int v1 = 1;

  void m1() {
    int v2 = 2;

    class X {
      int v3 = 3;

      void f() {
        int v4 = 4;
        System.out.printf("v4 = %d\n", v4); // f()의 로컬 변수
        System.out.printf("v3 = %d\n", this.v3); // X 인스턴스의 변수
        System.out.printf("v1 = %d\n", D2.this.v1); // D2의 인스턴스 변수

        // 바깥 메서드의 로컬 변수를 사용할 때는
        // 자신의 변수인양 사용하면 된다.
        System.out.printf("v2 = %d\n", v2); // D2.m1()의 로컬 변수
      }
    }

    X obj = new X(); // ==> X obj = new X(this, v2);
    obj.f();
  }
}

public class Exam0321 {

  public static void main(String[] args) {
    D2 obj = new D2();
    obj.m1();
  }

}
