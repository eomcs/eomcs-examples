package com.eomcs.tdd.ch03.step01.red;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// 동일성 비교 테스트 작성
// - equals()가 없으므로 두 Dollar(5)는 같지 않다고 판단된다. → 실패
//
class DollarTest {

  @Test
  void testMultiplication() {
    Dollar five = new Dollar(5);

    Dollar product = five.times(2);
    assertEquals(10, product.amount);

    product = five.times(3);
    assertEquals(15, product.amount);
  }

  @Test
  void testEquality() {
    // 같은 값을 가진 Dollar는 동등해야 한다.
    assertTrue(new Dollar(5).equals(new Dollar(5)));
  }
}
