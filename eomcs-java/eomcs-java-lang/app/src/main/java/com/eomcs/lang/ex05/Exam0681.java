package com.eomcs.lang.ex05;

//# 증감 연산자 : 전위(pre-fix) 증감 연산자 응용 II
//
public class Exam0681 {
  public static void main(String[] args) {
    int a = 5;
    int r = --a + --a / --a;
    System.out.printf("%d, %d\n", a, r);
    // 연산 순서
    // r = 4 + --a / --a;
    // r = 4 + 3 / --a;
    // r = 4 + 3 / 2;
    // r = 4 + 1;
    // r = 5;
  }
}






