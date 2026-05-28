package com.eomcs.cleancode.ch12.exam02;

// 예제 1: 테스트가 없으면 버그를 발견하기 어렵다 - Calculator
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: 테스트 없이 구현 — 버그가 숨어 있어도 발견하기 어렵다
  //   - add()인데 뺄셈을 하고 있다
  //   - 코드만 보면 문제를 발견하기 어렵다
  //   - 설계를 아무리 잘해도 동작이 틀리면 의미 없다
  static class BadCalculator {

    public int add(int a, int b) {
      return a - b; // 버그!
    }
  }

  // Good: 테스트가 동작을 정의한다
  //   - 잘못된 구현이 바로 드러난다
  //   - 설계 개선의 출발점이 된다
  static class Calculator {

    public int add(int a, int b) {
      return a + b;
    }
  }
}
