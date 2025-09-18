package com.eomcs.lang.ex06;

// # 흐름 제어문 - for 반복문의 초기화 변수
//
public class Exam0421 {
  public static void main(String[] args) {
    // 변수초기화 시키는 문장에 여러 개의 변수를 선언 할 수 있다.
    // 변수 증가문에 여러 개의 문장을 작성할 수 있다.
    for (int i = 1, j = 3, k = 5; i <= 10; i++, j--, k += 2)
      System.out.printf("(%d,%d,%d) ", i, j, k);
    System.out.println();
  }
}


