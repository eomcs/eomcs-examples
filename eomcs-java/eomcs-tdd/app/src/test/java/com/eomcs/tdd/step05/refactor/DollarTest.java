package com.eomcs.tdd.step05.refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DollarTest {

  @Test
  void testMultiplication() {
    Dollar five = new Dollar(5);

    // 객체 필드에 직접 접근하는 대신에 equals()를 이용해 Dollar 객체끼리 비교
    // 변경 후에도 테스트는 그대로 통과한다 (Green 유지).
    // 그리고 이제 테스트에서 amount를 직접 참조하지 않으므로
    // amount를 private으로 선언할 수 있다.
    assertEquals(new Dollar(10), five.times(2));
    assertEquals(new Dollar(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
  }
}
