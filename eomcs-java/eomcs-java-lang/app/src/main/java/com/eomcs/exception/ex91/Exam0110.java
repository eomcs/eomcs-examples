// 예외 발생 I
package com.eomcs.exception.ex91;

public class Exam0110 {

  public static void main(String[] args) {

    int result = 100 / 0; // 산술 연산 오류!
    // - 예외가 발생하면,
    //   JVM은 예외 정보를 출력한 후
    //   즉시 실행을 멈춘다.

    System.out.printf("계산 결과 = %d\n", result);
  }
}
















