// anonymous class - 여러 개의 인터페이스를 구현할 수 있을까?
package com.eomcs.oop.ex11.e;

public class Exam0140 {

  interface A {
    void m1();
  }

  interface B {
    void m2();
  }

  class X implements A, B {
    public void m1() {};
    public void m2() {}
  }
  
  public static void main(final String[] args) {
    // 여러 개의 인터페이스를 구현할 수 있을까?
    // => 안된다. 익명 클래스에는 그런 문법은 없다.
    //
    Object obj = new A, B() { // 컴파일 오류!
      @Override
      public void print() {
        System.out.println("Hello!");
      }
    };
    obj.print();
  }
}
