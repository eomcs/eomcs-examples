package com.eomcs.tdd.ch05.green;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// [Green 단계]
// Dollar를 복사·붙여넣기 하여 만든 Franc으로 테스트가 통과한다.
//
// 테스트 코드 자체는 [Red 단계]와 완전히 동일하다.
// Dollar의 testMultiplication과도 구조가 동일하다.
// → 테스트 코드도 중복이다. 이 역시 Refactor 단계의 숙제다.
class FrancTest {

  @Test
  void testFrancMultiplication() {
    Franc five = new Franc(5);
    assertEquals(new Franc(10), five.times(2));
    assertEquals(new Franc(15), five.times(3));
  }
}
