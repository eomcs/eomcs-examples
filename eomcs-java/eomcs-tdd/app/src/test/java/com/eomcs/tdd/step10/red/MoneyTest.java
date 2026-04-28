package com.eomcs.tdd.step10.red;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MoneyTest {

  // currency() 테스트 추가
  @Test
  void testCurrency() {
    // Money 클래스에 currency()가 없으므로 컴파일 오류 발생 → Red
    // assertEquals("USD", Money.dollar(1).currency());
    // assertEquals("CHF", Money.franc(1).currency());
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
    assertFalse(Money.dollar(5).equals(Money.franc(5)));
  }
}
