package com.eomcs.tdd.step04.green;

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
    assertFalse(new Dollar(5).equals(new Dollar(6)));
  }
}
