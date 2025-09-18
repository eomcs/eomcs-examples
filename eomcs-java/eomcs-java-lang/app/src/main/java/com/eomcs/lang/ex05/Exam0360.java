// 논리 연산자 : 비트 연산
package com.eomcs.lang.ex05;

public class Exam0360 {
  public static void main(String[] args) {
    // &&, ||, ! 의 피연산자(operand)는 반드시 boolean 이어야 한다.
    // 그리고 계산 결과는 boolean이다.
    boolean r;
    //    r = 10 && 20; // 컴파일 오류!
    //    r = 10 || 20; // 컴파일 오류!

    // &, |, ^, ~(not) 의 피연산자가 정수라면
    // 비트 연산자로 동작한다.

    //    r = 10 & 20; // 컴파일 오류! 비트 연산의 결과는 정수이다.

    int r2 = 10 & 20; // OK!

    //    float r3 = 10.2f & 20.3f; // 컴파일 오류!
  }
}

