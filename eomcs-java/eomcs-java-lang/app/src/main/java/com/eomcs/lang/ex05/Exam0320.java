package com.eomcs.lang.ex05;

//# 논리 연산자 : &, |
//
public class Exam0320 {
  public static void main(String[] args) {
    // AND 연산자
    // - 두 개의 논리 값이 모두 true일 때 결과가 true가 된다.
    System.out.println(true & true);
    System.out.println(true & false);
    System.out.println(false & true);
    System.out.println(false & false);

    System.out.println("-----------------------");

    // OR 연산자
    // - 두 개의 논리 값 중 한 개라도 true이면 결과는 true가 된다.
    System.out.println(true | true);
    System.out.println(true | false);
    System.out.println(false | true);
    System.out.println(false | false);

  }
}
