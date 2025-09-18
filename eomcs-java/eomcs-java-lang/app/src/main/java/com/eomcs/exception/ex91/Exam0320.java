// 예외의 종류 - 시스템 예외
package com.eomcs.exception.ex91;

public class Exam0320 {

  public static void main(String[] args) {

    try {
      long result = sum(100000);
      System.out.printf("계산 결과 = %d\n", result);

    } catch (Throwable e) {
      // 예외에 대한 상세 정보 출력
      e.printStackTrace();
    }
  }

  static long sum(long value) {
    if (value == 0)
      return 0;

    return value + sum(value - 1);
  }
}


//
// 시스템 예외
// - JVM 내부에서 발생하는 예외이다.
// - java.lang.Error 타입의 예외이다.
// - 애플리케이션 예외와 다르게
//   발생하는 즉시 프로그램을 종료해야 한다.
// - JVM 내부의 문제이기 때문에
//   애플리케이션에서 처리할 수 없다.
// - 시스템 예외의 경우 보통 예외 상황에 대해 기록을 남기고,
//   현재까지 작업한 내용을 보관한 후에 프로그램을 종료한다.

// [java.lang.Error]
// - 시스템 예외가 발생할 때 전달되는 예외 객체이다.
// - 시스템 예외 정보를 담고 있다.
// - 클래스 계층도
//    java.lang.Object
//      ㄴ java.lang.Throwable
//           ㄴ java.lang.Error
// - 시스템 예외 예:
//   StackOverflowError, OutOfMemoryError,
//   IOError, ThreadDeath 등
//
// 예외 클래스 계층도
// [java.lang.Throwable]
//     ㄴ java.lang.Error : 시스템 예외
//     ㄴ java.lang.Exception : 애플리케이션 예외
//














