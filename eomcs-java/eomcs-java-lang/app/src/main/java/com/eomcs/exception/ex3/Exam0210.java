// 예외 던지기 - 예외 상황을 호출자에게 알려주기
package com.eomcs.exception.ex3;

import java.io.FileNotFoundException;

public class Exam0210 {

  // throw 명령어를 사용하여 예외 정보를 호출자에게 던진다.
  // => throw [java.lang.Throwable 타입의 객체];
  //
  // java.lang.Throwable
  // => Throwable에는 두 부류의 서브 클래스가 있다.
  // 1) java.lang.Error (시스템 오류)
  //    => JVM에서 발생된 오류이다.
  //    => 개발자가 사용하는 클래스가 아니다.
  //    => 이 오류가 발생하면 현재의 시스템 상태를 즉시 백업하고, 실행을 멈춰야 한다.
  //    => JVM에서 오류가 발생한 경우에는 계속 실행해봐야 소용이 없다.
  //       근본적으로 문제를 해결할 수 없다.
  //    => 오류의 예:
  //       스택 오버 플로우 오류, VM 관련 오류, AWT 윈도우 관련 오류, 스레드 종료 오류 등
  //
  // 2) java.lang.Exception (애플리케이션 오류)
  //    => 애플리케이션에서 발생시킨 오류이다.
  //    => 개발자가 사용하는 클래스이다.
  //    => 적절한 조치를 취한 후 계속 시스템을 실행하게 만들 수 있다.
  //    => 오류의 예:
  //       배열의 인덱스가 무효한 오류, I/O 오류, SQL 오류, Parse 오류, 데이터 포맷 오류 등
  //

  // 오류를 던진다면 반드시 메서드 선언부에 어떤 오류를 던지는지 선언해야 한다.
  // => 메서드 호출자에게 알려주는 것이다.
  static void m1() throws Throwable {
    throw new Throwable(); // OK!
    // 예외를 던질 때 Throwable 클래스를 직접 사용하지 말라!
    // 그 하위 클래스를 사용하라.
    // 특히 애플리케이션 오류를 의미하는 Exception 클래스를 사용하라.
  }

  // 여러 개의 오류를 던지는 경우 메서드 선언부에 그대로 나열하라.
  static void m2() throws FileNotFoundException, RuntimeException {
    int a = 100;
    if (a < 0)
      throw new FileNotFoundException(); // OK!
    else
      throw new RuntimeException(); // OK!
  }

  public static void main(String[] args) {}

}
