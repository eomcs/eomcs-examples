package com.eomcs.lang.ex06;

//# 흐름 제어문 - if ~ else 문
//
public class Exam0141 {
  public static void main(String[] args) {
    int age = 17;

    // 잘못 사용한 예:
    // => else 문만 단독으로 사용할 수 없다.
    //    항상 if 문과 짝을 이뤄 사용한다.
    //    else
    //      System.out.println("컴파일 오류!");

    // => if 문과 else 문 사이에 다른 문장이 올 수 없다.

    //    if (age >= 19) 
    //      System.out.println("성인입니다.");
    //    System.out.println("--------------------------");
    //    else
    //      System.out.println("미성년입니다.");


  }
}