package com.eomcs.tdd.step06.green;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FrancTest {

  // 테스트 코드는 Dollar의 testMultiplication과 거의 동일하다.
  // Dollar → Franc, $ → CHF 로 바꾼 것뿐이다.
  @Test
  void testFrancMultiplication() {
    Franc five = new Franc(5);
    assertEquals(new Franc(10), five.times(2));
    assertEquals(new Franc(15), five.times(3));
  }

  // 이름을 testMultiplication에서 testDollarMultiplication으로 바꾼다.
  @Test
  void testDollarMultiplication() {
    Dollar five = new Dollar(5);
    assertEquals(new Dollar(10), five.times(2));
    assertEquals(new Dollar(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
  }
}
