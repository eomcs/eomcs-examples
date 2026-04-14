package com.eomcs.tdd.ch12.step02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step02 - Green] $5 + $5 = $10 테스트 통과
//
// 등장한 새 객체들:
//   Expression  - plus()의 반환 타입 (Sum과 Money가 구현)
//   Sum         - 두 Expression의 합 (augend + addend), 지연 계산
//   Bank        - reduce()로 Expression을 최종 Money로 변환
class MoneyTest {

  @Test
  void testSimpleAddition() {
    Money five = Money.dollar(5);
    Expression sum = five.plus(five);     // Sum(five, five) 반환 — 아직 계산 안 함
    Bank bank = new Bank();
    Money reduced = bank.reduce(sum, "USD"); // 이 시점에 5 + 5 = 10 계산
    assertEquals(Money.dollar(10), reduced);
  }

  @Test
  void testPlusReturnsSum() {
    // plus()가 Sum을 반환하는지, augend/addend가 올바른지 확인한다.
    Money five = Money.dollar(5);
    Expression result = five.plus(five);

    Sum sum = (Sum) result;
    assertEquals(five, sum.augend);
    assertEquals(five, sum.addend);
  }

  @Test
  void testReduceSum() {
    // Sum을 직접 reduce하면 두 amount를 합산한 Money를 반환한다.
    Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
    Bank bank = new Bank();
    Money result = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(7), result);
  }

  @Test
  void testReduceMoney() {
    // Money 자체도 Expression이므로 직접 reduce할 수 있다.
    Bank bank = new Bank();
    Money result = bank.reduce(Money.dollar(1), "USD");
    assertEquals(Money.dollar(1), result);
  }

  @Test
  void testMultiplication() {
    Money five = Money.dollar(5);
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
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
