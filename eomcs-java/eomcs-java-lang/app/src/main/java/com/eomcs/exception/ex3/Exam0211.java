// 예외 던지기 - 예외 상황을 호출자에게 알려주기
package com.eomcs.exception.ex3;

public class Exam0211 {

  // Error 계열의 예외를 던져서는 안되지만,
  // 혹 던진다고 한다면
  // 다음과 같이 메서드 선언부에 던지는 예외를 표시(선언)해도 되고,
  static void m1() throws Error {
    throw new Error();
    // OK! 하지만 이 계열의 클래스를 사용하지 말라!
    // 왜? JVM 관련 오류일 때 사용하는 클래스이다.
  }

  // 또는 다음과 같이 선언하지 않아도 된다.
  static void m2() {
    throw new Error();
  }
  // 즉 Error 계열의 예외를 던질 경우, 메서드 선언부에 표시하는 것은 선택사항이다.

  public static void main(String[] args) {}

}
