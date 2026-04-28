package com.eomcs.tdd.step03.green;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    // 같은 값을 가진 Dollar는 동등해야 한다.
    assertTrue(new Dollar(5).equals(new Dollar(5)));
  }
}
