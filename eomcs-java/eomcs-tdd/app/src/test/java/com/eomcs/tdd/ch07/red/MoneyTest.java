package com.eomcs.tdd.ch07.red;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [Red] 교차 통화 비교 테스트
//
// 기존 테스트는 모두 통과하지만,
// Dollar와 Franc을 비교하는 테스트를 추가하면 실패한다.
//
// 왜 실패하는가?
//   new Dollar(5).equals(new Franc(5))
//   → Money.equals() 호출
//   → 클래스 검사 없이 amount만 비교: 5 == 5 → true
//   → assertFalse(true) → 테스트 실패!
class MoneyTest {

  @Test
  void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
    assertTrue(new Franc(5).equals(new Franc(5)));
    assertFalse(new Franc(5).equals(new Franc(6)));

    // 교차 통화 비교: Dollar(5) 와 Franc(5) 는 달라야 한다.
    // → 현재 Money.equals()는 클래스 검사가 없어 amount만 비교한다.
    // → 5 == 5 이므로 true 반환 → 아래 테스트 실패!
    assertFalse(new Dollar(5).equals(new Franc(5))); // ← Red: 실패
  }
}
