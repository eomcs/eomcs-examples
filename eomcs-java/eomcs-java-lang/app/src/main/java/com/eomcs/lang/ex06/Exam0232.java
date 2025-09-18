package com.eomcs.lang.ex06;

//# 흐름 제어문 - switch 문법
//
public class Exam0232 {
  public static void main(String[] args) {
    // switch (값) {}
    // 값으로 가능한 데이터 타입은?
    // => int 정수(byte,short,int,char), 문자열, 특별한 상수 Enum 타입
    // => case 값으로 변수를 사용할 수 없다. 리터럴만 가능하다.

    // 컴파일 오류
    // - 4바이트를 넘어가는 정수는 사용할 수 없다.
    //    long l = 2;
    //    switch (l) {
    //      case 1:
    //      case 2:
    //      default:
    //    }

    // 컴파일 오류
    // - 부동소수점은 사용할 수 없다.
    //    float f = 2;
    //    switch (f) {
    //      case 1.1f:
    //      case 2.7f:
    //      default:
    //    }

    // 컴파일 오류
    // - boolean 값을 switch와 case에 사용할 수 없다.
    //    boolean bool = true;
    //    switch (bool) {
    //      case true:
    //      case false:
    //    }
  }
}