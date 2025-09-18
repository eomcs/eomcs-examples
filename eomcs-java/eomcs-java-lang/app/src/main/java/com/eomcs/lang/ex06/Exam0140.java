package com.eomcs.lang.ex06;

//# 흐름 제어문 - if ~ else 문
//
public class Exam0140 {
  public static void main(String[] args) {
    int age = 17;

    // if 문은 else 문 없이 단독으로 사용할 수 있다.
    if (age < 19)
      System.out.println("미성년입니다.1");
    if (age >= 19)
      System.out.println("성년입니다.1");

    // else문을 사용하는 아주 모범적인 예!
    if (age >= 19) // 조건이 거짓이면 다음 else 문을 실행한다.
      System.out.println("성인입니다.2");
    else
      System.out.println("미성년입니다.2");

    // 여러 문장을 실행할 때 블록으로 묶어야 한다.
    if (age >= 20) {
      System.out.println("--------------");
      System.out.println("성인입니다.3");
    }
    else {
      System.out.println("--------------");
      System.out.println("미성년입니다.3");
    }

    // 한 문장일 때는 블록으로 묶지 않아도 된다.
    if (age >= 19) { // OK!
      System.out.println("성인입니다.4");
      System.out.println("--------------------------");
    }
    else 
      System.out.println("미성년입니다.4");
    
    // 한 문장일 때는 블록으로 묶지 않아도 된다.
    if (age >= 19)
      System.out.println("성인입니다.5");
    else {
      System.out.println("미성년입니다.5");
      System.out.println("--------------------------");
    }
  }
}