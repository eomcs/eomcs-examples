package com.eomcs.lang.ex04;

//# 형변환 - 정수 변수 ==> 부동소수점 변수
//
public class Exam0920 {
  public static void main(String[] args) {
    byte b = 100;
    short s = 100;
    int i = 98765678;
    long l = 18_2345_3456_4567_5678L;
    char c = 100;

    float f;
    double d;

    // 정수 변수의 값을 부동소수점 변수에 저장할 수 있다.
    // 유효자릿수를 초과한 경우 적절한 반올림 처리한 후에 나머지 값이 짤린다.
    //
    f = b; // byte(1) ==> float(4). 값을 그대로 저장.
    f = s; // short(2) ==> float(4). 값을 그대로 저장. 
    f = c; // char(2) ==> float(4). 값을 그대로 저장.
    f = i; // int(4) ==> float(4). 값이 짤릴 수 있다.
    f = l; // long(8) ==> float(4). 값이 짤릴 수 있다.

    d = i; // int(4) ==> double(8), 유효자릿수라면 그대로 저장할 수 있다.
    d = l; // 유효 범위를 넘어가는 정수인 경우 짤린다.   
  }
}
