package com.eomcs.tdd.step04.red;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
    assertTrue(new Dollar(5).equals(new Dollar(5)));

    // Triangulation 기법
    // - 이 테스트를 추가하면 return true; 는 더 이상 통과하지 못한다.
    // - 이제 amount 값을 실제로 비교하는 일반적인 구현이 필요해진다.
    assertFalse(new Dollar(5).equals(new Dollar(6)));
  }
}
