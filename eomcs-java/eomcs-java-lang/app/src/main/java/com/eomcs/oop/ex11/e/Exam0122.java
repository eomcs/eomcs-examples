// anonymous class : 클래스를 상속 받은 익명 클래스 정의 - 생성자
package com.eomcs.oop.ex11.e;

public class Exam0122 {

  // default constructor 가 없는 클래스
  static class A {
    String message;
    int value;

    A(String message) {
      this.message = message;
    }

    A(String message, int value) {
      this.message = message;
      this.value = value;
    }

    void print() {
      System.out.printf("A의 print(): %s, %d\n", this.message, this.value);
    }
  }

  public static void main(final String[] args) {
    // 3) 익명 클래스의 생성자
    // => 익명 클래스의 객체를 만들 때 호출하는 생성자는
    //    수퍼 클래스에 존재하는 생성자여야 한다.
    //
    // 문법:
    // => 수퍼클래스 레퍼런스 = new 수퍼클래스(파라미터, ...) {};
    //    - 컴파일러는 호출하는 수퍼 클래스의 생성자와 동일한 파라미터를 갖는 생성자를
    //      익명 클래스에 추가한다.
    //
    A obj2 = new A("오호라!", 100) {
      // bytecode를 확인해 보라!
      //      $1(String arg0, int arg1) {
      //        super(arg0, arg1);
      //      }
    };

    obj2.print();

  }
}
