package com.eomcs.lang.ex06;

//# 흐름 제어문 - for 반복문
//
public class Exam0413 {
  public static void main(String[] args) {
    // for (변수선언 및 초기화; 조건; 증감문) 문장;
    // for (변수선언 및 초기화; 조건; 증감문) {문장1; 문장2; ...}

    // 조건문 제거
    int i = 1;
    for (  ;  ;  ) {
      if (i > 5)
        break;
      System.out.println(i);
      i++;
    }

  }
}
