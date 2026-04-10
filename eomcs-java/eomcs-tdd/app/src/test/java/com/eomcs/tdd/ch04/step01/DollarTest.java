package com.eomcs.tdd.ch04.step01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// 문제점:
// - testMultiplication()에서 amount 필드에 직접 접근한다.
// - 테스트가 객체 내부 구현(amount 필드)에 강하게 결합되어 있다.
//
class DollarTest {

  @Test
  void testMultiplication() {
    Dollar five = new Dollar(5);

    Dollar product = five.times(2);
    assertEquals(10, product.amount); // ← amount에 직접 접근! 캡슐화를 막는 코드

    product = five.times(3);
    assertEquals(15, product.amount); // ← 마찬가지
  }

  @Test
  void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
  }
}
