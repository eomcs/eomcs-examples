package com.eomcs.tdd.ch06.refactor02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [Refactor 02] equals()를 Money로 올렸지만 모든 테스트는 여전히 통과한다.
//
// Dollar는 이제 equals()를 Money에서 상속받는다.
// Franc은 아직 자신의 equals()를 가지고 있다.
// → 두 경우 모두 동작은 동일하다.
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
