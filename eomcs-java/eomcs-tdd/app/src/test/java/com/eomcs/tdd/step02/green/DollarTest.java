package com.eomcs.tdd.step02.green;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DollarTest {

  @Test
  void testMultiplication() {
    // 같은 five 객체로 두 번 곱셈해도 five의 상태는 변하지 않아야 한다.
    Dollar five = new Dollar(5);

    // times()가 새 Dollar 객체를 반환하도록 변경했으므로, five 객체는 상태가 변하지 않고 그대로 5를 유지한다.
    Dollar ten = five.times(2);
    assertEquals(10, ten.amount);

    // times()가 새 Dollar 객체를 반환하도록 변경했으므로, five 객체는 상태가 변하지 않고 그대로 5를 유지한다.
    Dollar fifteen = five.times(3);
    assertEquals(15, fifteen.amount);
  }
}
