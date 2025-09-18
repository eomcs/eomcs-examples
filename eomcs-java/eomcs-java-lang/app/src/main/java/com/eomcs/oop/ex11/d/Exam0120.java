// local class : 사용 범위
package com.eomcs.oop.ex11.d;

class B {
  void m1() {
    class X {

    }
    X obj = new X();
  }

  static void m2() {
    // 다른 메서드에 정의된 클래스는 사용할 수 없다. 
    //    X obj; // 컴파일 오류!
  }
}

public class Exam0120 {

  public static void main(String[] args) {
  }

}
