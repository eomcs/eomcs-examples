// anonymous class - 생성자 II
package com.eomcs.oop.ex11.e;

public class Exam0220 {

  static class A {
    public A() {
      System.out.println("A() 호출!");
    }
  }

  public static void main(final String[] args) {

    A obj = new A() {

      // 1) 익명 클래스는 생성자를 직접 정의할 수 없다.
      // - 그러나 컴파일러가 컴파일할 때 익명 클래스의 생성자를 만든다.
      //
      // 2) 대신 인스턴스 블록으로 생성자를 대신한다.
      // - 인스턴스 블록에 작성한 코드는
      //   결국 컴파일러가 자동 생성한 생성자에 들어간다.
      // - 그래서 인스턴스 블록에 작성한 코드가 실행될 것이다.
      //
      // 3) 물론 그전에 먼저 수퍼 클래스의 생성자가 실행될 것이다.
      {
        System.out.println("익명 클래스의 인스턴스 블록 실행!");
      }

      // 컴파일러는 익명 클래스의 생성자를 다음과 같이 추가할 것이다.
      //      A$1() {
      //        super();
      //        System.out.println("익명 클래스의 인스턴스 블록 실행!");
      //      }

    };
  }
}

// 수퍼 클래스 생성자 호출
// - 모든 클래스의 생성자는 항상 수퍼 클래스의 생성자를 먼저 호출한다.
//
