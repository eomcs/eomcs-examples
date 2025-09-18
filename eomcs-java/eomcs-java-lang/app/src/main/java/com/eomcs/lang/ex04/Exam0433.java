package com.eomcs.lang.ex04;

//# 문자 변수 - 따옴표('', single quotation) 활용하기
//
public class Exam0433 {
  public static void main(String[] args) {

    // 문자 코드 값을 모른다면 따옴표를 사용하라!
    // - 문법
    //   '문자' : UCS-2에 정의된 코드 값을 리턴한다.
    //
    char c = 'A';  // c 변수에 저장되는 것은 문자 'A'의 UCS-2 코드 값이다.
    System.out.println(c);

    //문자의 UCS-2 코드 값을 확인하기
    //- 정수 변수를 사용하면 문자의 코드 값을 받아서 그래도 출력할 수 있다.
    //  println()은 int 변수의 값은 10진수로 출력한다.
    int i = 'A';
    System.out.println(i);
  }
}
