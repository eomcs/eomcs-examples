package com.eomcs.tdd.ch07.green;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [Green] getClass() 추가 후 모든 테스트 통과
//
// 교차 통화 비교 테스트가 추가되었고, getClass() 검사 덕분에 통과한다.
//   new Dollar(5).equals(new Franc(5))
//   → Dollar.getClass() = Dollar.class
//   → Franc.getClass()  = Franc.class
//   → Dollar.class != Franc.class → false
//   → assertFalse(false) → 통과!
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

    // 교차 통화 비교: Dollar(5) 와 Franc(5) 는 달라야 한다. → 통과!
    assertFalse(new Dollar(5).equals(new Franc(5)));
  }
}
