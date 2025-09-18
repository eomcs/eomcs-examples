package com.eomcs.lang.ex06;

//# 흐름 제어문 - for 반복문
//
public class Exam0410 {
  public static void main(String[] args) {
    // for (변수선언 및 초기화; 조건; 증감문) 문장;
    // for (변수선언 및 초기화; 조건; 증감문) {문장1; 문장2; ...}

    // while 문을 사용할 경우,
    //    int a = 1;
    //    while (a <= 5) { 
    //      System.out.println(a++);
    //    }

    // for 문의 전형적인 예
    for (int i = 1; i <= 5; i++)
      System.out.println(i);
    // 실행 순서
    // 1) 변수초기화  => int i = 1
    // 2) 조건 => i <= 5
    // 3) 문장 => System.out.print(i + " ")
    // 4) 변수증가문 => i++
    // 조건이 참인 동안 2 ~ 4를 반복한다.

    // for 문에서 선언한 변수는 그 for 문 안에서만 사용할 수 있다.
    //    System.out.println(i); // 컴파일 오류!
  }
}
