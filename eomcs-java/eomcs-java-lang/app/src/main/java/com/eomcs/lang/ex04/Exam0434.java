// 문자 변수 - UCS-2 코드 확인하기
package com.eomcs.lang.ex04;

public class Exam0434 {
  public static void main(String[] args) {

    // 자바는 문자를 저장할 때 UCS-2에 정의된 정수 값을 저장한다.
    // 확인해 보자!
    
    // 영어 대문자의 문자 코드 저장
    int v = 0x41;  // 65(10진수)

    // 코드 값을 1씩 증가하면서 문자를 출력해 보자!
    for (int i = 0; i < 26; i++) {
      // 정수를 그냥 출력하면 10진수를 출력한다.
      // 정수가 문자 코드임을 알려줘야만 println()은 해당 코드의 문자를 출력한다.
      // 문법
      //   (char) 정수
      //System.out.print(v + i);
      System.out.print((char)(v + i));
      System.out.print(' ');
    }
  }
}

