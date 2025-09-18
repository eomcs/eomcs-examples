// 예외 던지기 - 예외 상황을 호출자에게 알려주기
package com.eomcs.exception.ex3;

public class Exam0212 {

  // Exception 계열의 예외를 던질 경우,
  // 반드시 메서드 선언부에 어떤 예외를 던지는지 지정해야 한다.
  // => 보통 개발자가 애플리케이션을 작성하면서
  //    예외를 던질 경우 이 클래스(및 하위 클래스)를 사용한다.
  static void m1() throws Exception {
    throw new Exception();
    // OK! 보통 개발자가 사용하는 예외 클래스이다.
  }

  // Exception 예외를 던질 경우 반드시 메서드 선언부에 표시해야 한다.
  static void m2() { // 컴파일 오류!
    throw new Exception();
  }

  // 메서드의 throws 에 선언할 수 있는 클래스는 Throwable 타입만 가능한다.
  static void m3() throws String {
    throw new String(); // 컴파일 오류!
    // throw 로 던질 수 있는 객체는 오직 java.lang.Throwable 타입만 가능하다.
  }

  public static void main(String[] args) {}

}
