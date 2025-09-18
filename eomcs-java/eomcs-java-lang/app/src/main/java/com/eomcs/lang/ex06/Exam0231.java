package com.eomcs.lang.ex06;

//# 흐름 제어문 - switch 문법
//
public class Exam0231 {
  public static void main(String[] args) {
    // switch (값) {}
    // 값으로 가능한 데이터 타입은?
    // => int 정수(byte,short,int,char), 문자열, 특별한 상수 Enum 타입
    // => case 값으로 변수를 사용할 수 없다. 리터럴만 가능하다.

    // case에는 리터럴만 올 수 있다.
    // 즉 변수를 사용할 수 없다.
    int x = 1, y = 300;
    switch (x) {
      case 1 * 300: // OK
        //      case 1 * y: // 컴파일 오류!
    }
  }
}