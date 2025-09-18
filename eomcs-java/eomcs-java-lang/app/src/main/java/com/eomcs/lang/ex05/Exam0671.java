package com.eomcs.lang.ex05;

//# 증감 연산자 : 전위(pre-fix) 증감 연산자 응용 I
//
public class Exam0671 {
  public static void main(String[] args) {
    int a = 5;
    int r = --a;
    // 위의 문장은 다음 두 문장으로 변경된다.
    // a = a - 1;
    // int r = a;
    
    System.out.printf("%d, %d\n", a, r);
    // r = --a 연산 순서
    // 1) --a를 먼저 실행 => a = 4
    // 2) a변수의 값을 리턴 => r = 4;
    // 3) = 연산을 수행 => r 변수에 4 값을 넣는다.
  }
}
