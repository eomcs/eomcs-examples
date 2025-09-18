// 예외 처리 문법을 적용하기 전 - 예외 발생 시 시스템 멈춤 문제
package com.eomcs.exception.ex1;

import java.util.Scanner;

public class Exam0210 {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    while (true) {
      System.out.print("입력> ");
      String op = keyScan.next();
      if (op.equalsIgnoreCase("quit"))
        break;
      int v1 = keyScan.nextInt();
      int v2 = keyScan.nextInt();

      // 다음과 같이 일반적인 예외의 경우 리턴 값을 검사하여 처리하면 된다.
      // 문제는 0으로 나누는 경우에서 처럼 계산할 수 없는 예외 상황이 발생한 경우,
      // JVM은 실행을 종료하게 된다.
      // => 0으로 나눌 때처럼 예외가 발생하더라도 JVM을 멈추지 않고
      //    계속 정상적으로 실행되게 만드는 문법이 "예외처리"이다.
      int result = Calculator2.compute(op, v1, v2);
      if (result == -1212121212) {
        System.out.println("유효하지 않은 연산자입니다!");
      } else {
        System.out.println(result);
      }
    }
    keyScan.close();
  }
}


