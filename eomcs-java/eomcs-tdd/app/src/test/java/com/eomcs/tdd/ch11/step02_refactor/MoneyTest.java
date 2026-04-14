package com.eomcs.tdd.ch11.step02_refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step02_refactor] Dollar, Franc 클래스 모두 제거 후 테스트
//
// testDifferentClassEquality()는 Money와 Franc을 비교하는 테스트였다.
// Franc이 사라졌으므로 이 테스트는 더 이상 컴파일되지 않는다 → 제거
class MoneyTest {

  @Test
  void testDollarMultiplication() {
    Money five = Money.dollar(5);
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
  }

  @Test
  void testFrancMultiplication() {
    // times()가 Money로 올라가 Dollar/Franc 구분이 없어졌다.
    // testDollarMultiplication()과 동일한 로직을 검증하는 중복 테스트다.
    // step03에서 제거한다.
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

  @Test
  void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
  }

  // testDifferentClassEquality() 제거:
  // Franc 클래스가 사라졌으므로 "Money vs Franc" 비교 테스트는 의미 없다.
}
