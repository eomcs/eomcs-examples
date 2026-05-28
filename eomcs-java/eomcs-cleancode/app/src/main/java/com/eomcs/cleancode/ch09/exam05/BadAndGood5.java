package com.eomcs.cleancode.ch09.exam05;

// 예제 5: T — Timely (적시에 작성) - Calculator
public class BadAndGood5 {

  private BadAndGood5() {}

  static class Calculator {
    int add(int a, int b) {
      return a + b;
    }
  }
}
