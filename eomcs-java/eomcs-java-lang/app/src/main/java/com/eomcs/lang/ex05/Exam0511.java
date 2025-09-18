package com.eomcs.lang.ex05;

//# 조건 연산자 => ? :
//
public class Exam0511 {
  public static void main(String[] args) {
    // 조건연산자
    // => 조건 ? 표현식1 : 표현식2
    // => 조건이 참이면 표현식1을 실행하고,
    //    조건이 거짓이면 표현식2를 실행한다.
    //
    int age = 20;

    // 또는 파라미터 값이 놓일 자리에 조건연산자를 둘 수 있다.
    test(age > 18 ? "성년" : "미성년");
  }

  static void test(String value) {
    System.out.println(value + " 입니다.");
  }
}









