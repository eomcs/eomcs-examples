package com.eomcs.lang.ex03;

//# 부동소수점 리터럴 - 메모리 크기에 따른 표기법

public class Exam0320 {
  public static void main(String[] args) {
    
    //## 8바이트 크기의 부동소수점
    //- 숫자 맨 뒤에 d 또는 D를 붙인다.
    //- 생략해도 된다.
    System.out.println(3.14);
    System.out.println(3.14d);
    System.out.println(3.14D);

    //## 4바이트 크기의 부동소수점
    //- 숫자 맨 뒤에 f 또는 F를 붙인다.
    System.out.println(3.14f); // 주로 소문자 f를 많이 붙인다.
    System.out.println(3.14F);
  }
}

//## 정리
//4바이트 부동소수점
//  3.14F
//  3.14f (주로 사용)
//  31.4e-1f
//  31.4e-1F
//  
//8바이트 부동소수점
//  3.14
//  31.4e-1
//