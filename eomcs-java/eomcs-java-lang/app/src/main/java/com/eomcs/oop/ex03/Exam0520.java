// 변수 자동 초기화 - 인스턴스 변수
package com.eomcs.oop.ex03;

import java.sql.Date;

public class Exam0520 {

  static class A {
    byte b2;
    short s2;
    int i2;
    long l2;
    char c2;
    float f2;
    double d2;
    boolean bool2;
    String str2;
    Date date2;
  }

  public static void main(String[] args) {

    // 인스턴스 변수도 클래스 변수와 마찬가지이다.
    // 힙(heap)에 생성되는 인스턴스 필드(변수)는 자동으로 초기화된다.
    // 그래서 인스턴스 필드(변수)는 생성되는 즉시 0으로 초기화 된다.
    // => 정수(byte, short, int, long, char) : 0
    // => 부동소수점(float, double) : 0.0 = 0
    // => 논리(boolean) : false = 0
    // => 레퍼런스: null = 0
    A obj = new A();

    System.out.printf("%d, %d, %d, %d, %c, %.1f, %.1f, %b, %s, %s\n", 
        obj.b2, obj.s2, obj.i2, obj.l2, obj.c2, obj.f2, obj.d2, obj.bool2, obj.str2, obj.date2);
  }
}


