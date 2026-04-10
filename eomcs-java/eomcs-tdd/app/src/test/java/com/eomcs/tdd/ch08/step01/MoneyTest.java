package com.eomcs.tdd.ch08.step01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step01] times() 반환 타입을 Money로 바꿔도 기존 테스트는 그대로 통과한다.
//
// 아직 테스트 코드는 변경하지 않는다.
// Dollar five = new Dollar(5) 처럼 구체 클래스를 직접 사용 중이지만,
// times()가 Money를 반환해도 assertEquals()의 equals() 비교에는 문제 없다.
//
// 이 단계는 순수한 리팩토링이다:
//   외부 동작(테스트 결과)은 변하지 않고 내부 구조(반환 타입)만 개선된다.
class MoneyTest {

  @Test
  void testDollarMultiplication() {
    Dollar five = new Dollar(5);
    // five.times(2)의 실제 반환 타입은 Money이지만
    // equals()는 getClass()로 비교하므로 Dollar끼리 비교 → 통과
    assertEquals(new Dollar(10), five.times(2));
    assertEquals(new Dollar(15), five.times(3));
  }

  @Test
  void testFrancMultiplication() {
    Franc five = new Franc(5);
    assertEquals(new Franc(10), five.times(2));
    assertEquals(new Franc(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
    assertTrue(new Franc(5).equals(new Franc(5)));
    assertFalse(new Franc(5).equals(new Franc(6)));
    assertFalse(new Dollar(5).equals(new Franc(5)));
  }
}
