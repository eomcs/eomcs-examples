package com.eomcs.lang.ex04;

//# 문자 변수 - 변수의 메모리 크기
//
public class Exam0431 {
  public static void main(String[] args) {

    // 자바는 'UCS(Universal Character Set; ISO 10646)'라는 국제 표준 문자 인코딩 규격에 따라 문자를 다룬다.
    // UCS-2 라고도 부르며, 각 글자를 0 ~ 65535 까지의 16비트 코드 값으로 저장한다.

    // 자바는 UCS-2 코드 값을 저장하기 위해 2바이트 크기의 메모리를 사용한다.
    // 다음은 2바이트 크기의 UCS-2 코드 값을 저장할 변수를 선언한다.
    char c;

    c = 0;
    c = 65535;

    // char(2byte) 크기의 메모리 범위를 초과하면 문법 오류이다. 
    //c = -1; // 컴파일 오류 발생!
    //c = 65536; // 컴파일 오류 발생!

  }
}
