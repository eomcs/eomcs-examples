package com.eomcs.cleancode.ch12.exam02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.eomcs.cleancode.ch12.exam02.BadAndGood1.BadCalculator;
import com.eomcs.cleancode.ch12.exam02.BadAndGood1.Calculator;
import org.junit.jupiter.api.Test;

// 예제 1: 테스트가 없으면 버그를 발견하기 어렵다 - Calculator
//
// Bad: BadCalculator.add()는 a - b를 반환하므로 add(2, 3) = -1 이다.
//      테스트가 없으면 이 버그를 지나치기 쉽다.
// Good: Calculator.add()는 a + b를 반환하므로 add(2, 3) = 5 이다.
//       테스트가 올바른 동작을 정의하고, 잘못된 구현을 즉시 드러낸다.
class BadAndGood1Test {

  @Test
  void 올바른_덧셈_결과를_반환한다() {
    Calculator calculator = new Calculator();

    assertEquals(5, calculator.add(2, 3));
  }

  @Test
  void BadCalculator는_같은_테스트를_통과하지_못한다() {
    BadCalculator badCalculator = new BadCalculator();

    // add(2, 3)이 5가 아님을 보여준다 — 버그가 있는 구현이다
    assertEquals(5, badCalculator.add(2, 3));
  }
}
