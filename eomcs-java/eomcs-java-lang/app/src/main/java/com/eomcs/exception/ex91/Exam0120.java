// 예외 발생 II
package com.eomcs.exception.ex91;

public class Exam0120 {

  public static void main(String[] args) {

    long result = sum(100000); // 스택 메모리 오류!
    // - 예외가 발생하면,
    //   JVM은 예외 정보를 출력한 후
    //   즉시 실행을 멈춘다.

    System.out.printf("계산 결과 = %d\n", result);
  }

  static long sum(long value) {
    if (value == 0)
      return 0;

    return value + sum(value - 1);
  }
}











