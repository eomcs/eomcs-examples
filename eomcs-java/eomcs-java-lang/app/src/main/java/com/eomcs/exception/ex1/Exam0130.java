// 예외 처리 문법을 적용하기 전 - 리턴 값으로 오류를 알릴 때의 문제를 극복
package com.eomcs.exception.ex1;

public class Exam0130 {
  public static void main(String[] args) {

    int result = Calculator2.compute("#", 100, 200);

    // 예전에는 작업 실행중에 오류가 발생하면 희귀한 값을 리턴하여 알려줬다.
    if (result == -1212121212) {
      System.out.println("유효하지 않은 연산자입니다!");
    } else {
      System.out.println(result);
    }

    // 일반적인 결과는 정상적으로 수행된다.
    result = Calculator2.compute("-", 6, 7);
    if (result == -1212121212) {
      System.out.println("유효하지 않은 연산자입니다!");
    } else {
      System.out.println(result);
    }
  }
}


