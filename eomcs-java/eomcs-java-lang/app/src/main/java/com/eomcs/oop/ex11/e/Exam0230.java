// anonymous class - 호출할 수퍼 클래스의 생성자 지정하기
package com.eomcs.oop.ex11.e;

public class Exam0230 {

  static class A {

    String name;

    public A() {
      System.out.println("A() 호출됨!");
      name = "이름 없음";
    }

    public A(String name) {
      System.out.println("A(String) 호출됨!");
      this.name = name;
    }

    public void print() {
      System.out.println(name);
    }
  }

  public static void main(String[] args) {

    // 익명 클래스를 정의할 때 호출할 수퍼 클래스의 생성자를 지정할 수 있다.
    // - 문법:
    //     new 수퍼클래스명(파라미터,...) {}
    // - 즉 생성자에 넘겨주는 파라미터로 호출될 생성자를 지정한다.

    // 1) 수퍼 클래스의 기본 생성자 호출하기
    // - 컴파일러는 익명 클래스에 기본 생성자를 추가한다.
    //
    A obj = new A() {
      // 컴파일러는 다음과 같이 익명 클래스의 생성자를 추가한다.
      //      $1() { // 생성자를 추가한다.
      //        super(); // 수퍼 클래스의 기본 생성자를 호출한다.
      //      }
    };
    obj.print();

    System.out.println("-----------------------------");

    // 2) 수퍼 클래스의 다른 생성자 호출하기
    // - 다음과 같이 익명 클래스의 인스턴스를 만들 때 값을 지정하면
    //   그 타입의 값을 받는 수퍼 클래스의 생성자가 호출된다.
    // - 컴파일러는 익명 클래스에 String 파라미터를 가진 생성자를 추가한다.
    //
    obj = new A("홍길동") {
      // 컴파일러는 다음과 같이 익명 클래스의 생성자를 추가한다.
      //      $1(String arg0) { // 문자열을 받는 생성자를 추가한다.
      //        super(arg0); // 수퍼 클래스의 생성자 중에서 문자열을 받는 생성자를 호출한다.
      //      }
    };
    obj.print();

    // 물론 다음과 같이 해당 타입의 값을 받을 생성자가 없으면
    // 컴파일 오류가 발생한다.
    //    obj = new A(100) {}; // 컴파일 오류!
  }
}
