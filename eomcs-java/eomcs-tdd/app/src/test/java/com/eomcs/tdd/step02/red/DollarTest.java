package com.eomcs.tdd.step02.red;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DollarTest {

  @Test
  void testMultiplication() {
    // 같은 five 객체로 두 번 곱셈해도 five의 상태는 변하지 않아야 한다.
    Dollar five = new Dollar(5);

    five.times(2);
    assertEquals(10, five.amount);

    // five.amount = 10 이므로 10 * 3 = 30이 되어 실패한다.
    five.times(3);
    assertEquals(15, five.amount);
  }
}
