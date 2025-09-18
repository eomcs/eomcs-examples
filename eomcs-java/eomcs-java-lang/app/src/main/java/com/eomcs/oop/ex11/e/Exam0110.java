// anonymous class : 인터페이스를 구현한 익명 클래스 정의
package com.eomcs.oop.ex11.e;

public class Exam0110 {
  // 인터페이스의 경우 static으로 선언하지 않아도 스태틱 멤버에서 사용할 수 있다.
  // => 인터페이스는 규칙을 정의한 것이기 때문에 인스턴스 멤버라는 개념이 존재하지 않는다.
  //
  interface A {
    void print();
  }

  class X {}

  static class Y {}

  public static void main(final String[] args) {

    // 1) 로컬 클래스로 인터페이스 구현하기
    class My implements A {
      String name = "홍길동";

      @Override
      public void print() {
        System.out.printf("Hello, %s!\n", this.name);
      }
    }

    A a = new My();
    a.print();

    A a2 = new My();
    A a3 = new My();


    // 2) non-static 중첩 클래스 사용?
    //    X obj2 = new X(); // 컴파일 오류!

    // 3) static 중첩 클래스 사용
    Y obj3 = new Y(); // OK
  }
}