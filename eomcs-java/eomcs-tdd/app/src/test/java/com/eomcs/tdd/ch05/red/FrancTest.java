package com.eomcs.tdd.ch05.red;

import org.junit.jupiter.api.Test;

// [Red 단계]
// 새 요구사항: 5 CHF × 2 = 10 CHF
//
// Franc 클래스가 존재하지 않으므로 컴파일 자체가 되지 않는다.
// 이것이 Red 상태다.
//
// 테스트 코드는 Dollar의 testMultiplication과 거의 동일하다.
// Dollar → Franc, $ → CHF 로 바꾼 것뿐이다.
class FrancTest {

  @Test
  void testFrancMultiplication() {
    // Franc five = new Franc(5); // 컴파일 오류: Franc 클래스 없음
    // assertEquals(new Franc(10), five.times(2));
    // assertEquals(new Franc(15), five.times(3));
  }
}
