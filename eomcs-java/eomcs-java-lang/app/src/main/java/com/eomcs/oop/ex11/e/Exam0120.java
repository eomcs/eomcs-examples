// anonymous class : 클래스를 상속 받은 익명 클래스 정의
package com.eomcs.oop.ex11.e;

public class Exam0120 {

  // 클래스는 static과 non-static이 구분된다.
  static abstract class A {
    abstract void print();
  }

  public static void main(final String[] args) {
    // 1) 로컬 클래스로 서브 클래스 만들기
    class My extends A {
      @Override
      public void print() {
        System.out.println("Hello!");
      }
    }

    A obj = new My();
    obj.print();
  }
}
