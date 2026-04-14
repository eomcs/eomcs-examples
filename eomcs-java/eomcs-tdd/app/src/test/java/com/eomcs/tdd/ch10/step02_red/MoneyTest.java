package com.eomcs.tdd.ch10.step02_red;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step02_red] 실험을 통한 피드백
class MoneyTest {

  @Test
  void testDollarMultiplication() {
    Money five = Money.dollar(5);

    // 팩토리 메서드의 리턴 객체 타입과 times()의 리턴 객체 타입이 달라서 테스트 실패!
    // - Money.dollar(10): Dollar 객체 리턴
    // - five.times(2): Money 객체 리턴
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
  }

  @Test
  void testFrancMultiplication() {
    Money five = Money.franc(5);

    // 팩토리 메서드의 리턴 객체 타입과 times()의 리턴 객체 타입이 달라서 테스트 실패!
    // - Money.franc(10): Franc 객체 리턴
    // - five.times(2): Money 객체 리턴
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
}
