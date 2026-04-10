package com.eomcs.tdd.ch09.step02_refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step02 - Refactor] currency를 Money로 올리기
//
// 핵심 변화:
//   1. currency() → Money에서 직접 제공
class MoneyTest {

  @Test
  void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
  }

  @Test
  void testDollarMultiplication() {
    Money five = Money.dollar(5);
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
  }

  @Test
  void testFrancMultiplication() {
    Money five = Money.franc(5);
    assertEquals(Money.franc(10), five.times(2));
    assertEquals(Money.franc(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    assertTrue(Money.franc(5).equals(Money.franc(5)));
    assertFalse(Money.franc(5).equals(Money.franc(6)));
    // currency 기반 비교: amount가 같아도 통화가 다르면 false가 되어야 한다.
    // equals()에서 currency() 메서드를 호출하여 통화 단위도 비교하도록 변경해야 한다.
    assertFalse(Money.dollar(5).equals(Money.franc(5)));
  }
}
