// anonymous class - 수퍼 클래스와 인터페이스를 동시에 지정할 수 있을까?
package com.eomcs.oop.ex11.e;

public class Exam0130 {

  static abstract class A {
    public abstract void print();
  }

  interface B {
    String getName();
  }

  public static void main(final String[] args) {
    // 클래스도 상속 받고 인터페이스도 구현하는 익명 클래스를 만들 수 있을까?
    // => 안된다.
    // => 둘 중 하나만 상속 받거나 구현해야지, 동시에 다 할 수 없다.
    //
    A obj = new A implements B() { // 컴파일 오류!
      @Override
      public void print() {
        System.out.println("Hello!");
      }
      public String getName() {
        return "부질없는 짓";
      }
    };
    obj.print();
  }
}
