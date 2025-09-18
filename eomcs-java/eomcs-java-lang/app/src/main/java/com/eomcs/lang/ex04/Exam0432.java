package com.eomcs.lang.ex04;

//# 문자 변수 - UCS-2 문자 코드 값 저장
//
public class Exam0432 {
  public static void main(String[] args) {
    
    char c1, c2, c3;
    
    // 문자 'A'를 변수에 저장하는 방법?
    //- 문자 'A'의 UCS-2 코드 값을 저장하면 된다.
    //
    
    c1 = 65;            // 문자 'A'의 코드 값을 10진수로 표현한 것.
    c2 = 0x41;          // 문자 'A'의 코드 값을 16진수로 표현한 것.
    c3 = 0b0100_0001;   // 문자 'A'의 코드 값을 2진수로 표현한 것.

    // println()을 통해 문자를 출력할 수 있다.
    //- 변수의 종류가 char 이면 출력할 때 코드 값 대신 문자를 출력한다.
    //- 해당 코드의 문자를 폰트 파일에서 찾아 출력한다.
    //
    System.out.println(c1);
    System.out.println(c2);
    System.out.println(c3);
  }
}
