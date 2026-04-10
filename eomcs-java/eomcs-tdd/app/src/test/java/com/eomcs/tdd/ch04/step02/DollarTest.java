package com.eomcs.tdd.ch04.step02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// testMultiplication 리팩토링:
// - product.amount에 직접 접근하는 대신 equals()로 Dollar 객체끼리 비교한다.
//
// Before:
//   Dollar product = five.times(2);
//   assertEquals(10, product.amount);
//
// After:
//   assertEquals(new Dollar(10), five.times(2));
//
// 변경 후에도 테스트는 그대로 통과한다 (Green 유지).
// 그리고 이제 테스트에서 amount를 직접 참조하지 않으므로
// amount를 private으로 선언할 수 있다.
class DollarTest {

  @Test
  void testMultiplication() {
    Dollar five = new Dollar(5);
    // equals()를 이용해 Dollar 객체끼리 비교 → 내부 필드에 직접 접근할 필요 없음
    assertEquals(new Dollar(10), five.times(2));
    assertEquals(new Dollar(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
  }
}
