package com.eomcs.tdd.ch02.green;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// 해결책: times()가 새 Dollar를 반환하도록 변경
//
class DollarTest {

  @Test
  void testMultiplication() {
    // 같은 five 객체로 두 번 곱셈해도 five의 상태는 변하지 않아야 한다.
    Dollar five = new Dollar(5);

    Dollar product = five.times(2);
    assertEquals(10, product.amount);

    // ch01의 Dollar라면 이 시점에 five.amount = 10 이므로 10 * 3 = 30이 되어 실패한다.
    product = five.times(3);
    assertEquals(15, product.amount);
  }
}
