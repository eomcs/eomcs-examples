package com.eomcs.tdd.ch06.refactor03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [Refactor 03] Franc도 Money를 상속한 최종 상태
//
// 새 테스트 추가: Dollar(5)와 Franc(5)는 동등하지 않아야 한다.
// → 같은 amount를 가지더라도 통화가 다르면 다른 값이다.
// → Money.equals()의 getClass() 비교가 이를 보장한다.
//
class MoneyTest {

  @Test
  void testDollarMultiplication() {
    Dollar five = new Dollar(5);
    assertEquals(new Dollar(10), five.times(2));
    assertEquals(new Dollar(15), five.times(3));
  }

  @Test
  void testFrancMultiplication() {
    Franc five = new Franc(5);
    assertEquals(new Franc(10), five.times(2));
    assertEquals(new Franc(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
    assertTrue(new Franc(5).equals(new Franc(5)));
    assertFalse(new Franc(5).equals(new Franc(6)));
  }
}
