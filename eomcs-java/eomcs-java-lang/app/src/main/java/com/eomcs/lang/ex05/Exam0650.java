package com.eomcs.lang.ex05;

//# 증감 연산자 : 전위(pre-fix) 증가 연산자
//
public class Exam0650 {
  public static void main(String[] args) {
    int i = 2;

    ++i;
    // i 메모리의 값을 먼저 증가시킨다.
    // 그리고 i 메모리의 값을 그 자리에 놓는다.

    ++i;

    System.out.println(i); // 4

    System.out.println(++i); 
    // i = i + 1
    // System.out.println(i)

    System.out.println(i); // 5

  }
}
