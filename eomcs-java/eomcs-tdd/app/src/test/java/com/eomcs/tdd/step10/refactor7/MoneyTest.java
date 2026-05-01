package com.eomcs.tdd.step10.refactor7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MoneyTest {

  @Test
  void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
  }

  // testDollarMultiplication + testFrancMultiplication 통합
  // Dollar와 Franc의 구분이 사라졌으므로 하나로 합친다.
  @Test
  void testMultiplication() {
    Money five = Money.franc(5);
    assertEquals(Money.franc(10), five.times(2));
    assertEquals(Money.franc(15), five.times(3));
  }

  @Test
  void testEquality() {
    // 객체 생성 로직이 통합되면서, 달러와 프랑의 동등성 검증이 동일한 로직으로 검증된다.
    // 따라서, 달러와 프랑의 동등성 검증을 하나로 합치고, 다른 통화 간의 동등성 검증은 유지한다.
    assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    assertFalse(Money.dollar(5).equals(Money.franc(5)));
  }
}
