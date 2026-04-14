package com.eomcs.tdd.ch11.step01_refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step01_refactor] Dollar 클래스를 제거해도 모든 테스트는 그대로 통과한다.
//
// Money.dollar()가 Dollar 인스턴스 대신 Money 인스턴스를 반환하지만
// equals()가 currency 문자열로 비교하므로 동등성 판단에 문제 없다.
class MoneyTest {

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

  @Test
  void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
  }

  @Test
  void testDifferentClassEquality() {
    // Franc은 아직 존재하므로 이 테스트는 아직 유효하다.
    assertTrue(new Money(10, "CHF").equals(new Franc(10, "CHF")));
  }
}
