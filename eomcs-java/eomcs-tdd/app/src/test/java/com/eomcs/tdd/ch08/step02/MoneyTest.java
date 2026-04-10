package com.eomcs.tdd.ch08.step02;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step02] 팩토리 메서드 사용으로 테스트 변경
//
// Before (step01):
//   Dollar five = new Dollar(5);
//   assertEquals(new Dollar(10), five.times(2));
//
// After:
//   Money five = Money.dollar(5);
//   assertEquals(Money.dollar(10), five.times(2));
//
// 테스트가 Dollar, Franc 클래스를 전혀 import하지 않는다.
// → 테스트와 구체 클래스 간의 결합이 완전히 제거되었다.
// → Dollar/Franc의 내부 구현이 바뀌거나 클래스명이 바뀌어도 테스트는 수정 불필요.
//
// Money 클래스에 times() 메서드가 없기 때문에 컴파일 에러가 발생한다. (Red 상태)
class MoneyTest {

  @Test
  void testDollarMultiplication() {
    Money five = Money.dollar(5); // new Dollar(5) → Money.dollar(5)
    // assertEquals(Money.dollar(10), five.times(2)); // new Dollar(10) → Money.dollar(10)
    // assertEquals(Money.dollar(15), five.times(3));
  }

  @Test
  void testFrancMultiplication() {
    Money five = Money.franc(5); // new Franc(5) → Money.franc(5)
    // assertEquals(Money.franc(10), five.times(2));
    // assertEquals(Money.franc(15), five.times(3));
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
