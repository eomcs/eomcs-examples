package com.eomcs.cleancode.ch02.exam03;

// 숫자 시리즈 이름
public class BadAndGood1 {

  // Bad: 의도를 전혀 알 수 없다
  public static void copyChars1(char[] a1, char[] a2) {
    for (int i = 0; i < a1.length; i++) {
      a2[i] = a1[i];
    }
  }

  // Good: 역할을 드러내는 이름을 사용해야 한다.
  public static void copyChars2(char[] source, char[] destination) {
    for (int i = 0; i < source.length; i++) {
      destination[i] = source[i];
    }
  }
}
