package com.eomcs.tdd.step01.red;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// Red 단계
// - $5 × 2 = $10 테스트하는 코드를 작성한다.
// - Dollar 클래스는 개발자가 달러의 금액을 표현하고 곱셈 연산을 수행하는 역할을 메타포로 표현한 것이다.
//   개발자의 설계 의도를 반영하여 만든 클래스다.
// - Dollar 클래스를 정의하여 컴파일 오류를 해결한다.
// - 다만 assertEquals(10, five.amount) 테스트는 실패한다.
// - 이것이 바로 "Red" 상태다.

class DollarTest {
  @Test
  void testMultiplication() {
    // $5 × 2 = $10 이어야 한다
    Dollar five = new Dollar(5);
    five.times(2);
    assertEquals(10, five.amount);
  }
}
