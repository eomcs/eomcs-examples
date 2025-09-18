// 예외 처리 - 예외 발생과 예외 처리
package com.eomcs.exception.ex91;

public class Exam0220 {

  public static void main(String[] args) {

    // 예외 발생과 처리 과정
    // - 명령을 실행 중에 예외 발생하면,
    //   예외 상황을 객체에 담아 전달한다.
    // - 예외 처리 코드가 있다면,
    //   해당 코드를 실행한다.
    // - 예외 처리 코드가 없다면,
    //   예외 처리 코드를 만날 때까지
    //   메서드의 호출자를 따라 올라가면서 예외를 전달한다.
    //   main() 메서드를 호출한 곳까지 도달하면
    //   예외 정보를 출력한 후 즉시 실행을 멈춘다.
    //
    // 예외 객체
    // - 예외 정보를 담고 있다.
    // - java.lang.Throwable 타입의 객체다.
    //
    // 예외 처리 문법
    //      try {
    //          명령문;
    //          ...
    //      } catch (Throwable 예외객체를_받을_파라미터) {
    //          예외처리 명령문;
    //          ...
    //      }
    //
    try {
      long result = sum(100000);
      System.out.printf("계산 결과 = %d\n", result);

    } catch (Throwable e) {
      System.out.println("합계를 구하는 중 오류 발생!");

    }

    // 이번 예제의 경우처럼 JVM에서 발생한 문제(스택 메모리 부족)인 경우,
    // 예외 처리 블록에서 오류에 대한 내용을 파일에 기록한 후
    // 프로그램을 종료할 수 있다.
    // 이 경우에도 예외 처리 문법은 프로그래밍에 유용한 문법이다.
  }

  static long sum(long value) {
    if (value == 0)
      return 0;

    return value + sum(value - 1);
  }
}














