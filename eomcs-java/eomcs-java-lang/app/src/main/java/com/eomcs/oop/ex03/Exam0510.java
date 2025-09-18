// 변수 자동 초기화 - 스태틱 변수
package com.eomcs.oop.ex03;

import java.sql.Date;

public class Exam0510 {

  static class A {
    static byte b;
    static short s;
    static int i;
    static long l;
    static char c;
    static float f;
    static double d;
    static boolean bool;
    static String str;
    static Date date;
  }

  public static void main(String[] args) {

    // 클래스 변수(스태틱 변수)는 생성되는 순간 0으로 자동 초기화 된다.
    // => 정수(byte, short, int, long, char) : 0(모든 비트를 0으로 설정; 전기없는 상태)
    // => 부동소수점(float, double) : 0.0 = 0(모든 비트를 0으로 설정; 전기없는 상태)
    // => 논리(boolean) : false = 0(모든 비트를 0으로 설정; 전기없는 상태)
    // => 레퍼런스: null = 0(모든 비트를 0으로 설정; 전기없는 상태)
    //
    System.out.printf("%d, %d, %d, %d, %c, %.1f, %.1f, %b, %s, %s\n", 
        A.b, A.s, A.i, A.l, A.c, A.f, A.d, A.bool, A.str, A.date);

    // 로컬 변수는 자동으로 초기화되지 않는다.
    // 사용하기 전에 반드시 초기기화시켜야 한다.
    // 다음과 같이 초기화시키지 않은 상태에서 사용하면 컴파일 오류가 발생한다.
    // => 어떤 값으로 초기화시키나요? 
    //    변수를 만든 후 처음에 어떤 값으로 시작할 지 개발자가 결정한다.
    //
    //    int x;
    //    System.out.println(x); // 컴파일 오류!
  }
}


