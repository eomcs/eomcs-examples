package com.eomcs.tdd.ch09.step01_red;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step01 - Red] currency() 테스트 추가
//
// Dollar와 Franc의 차이를 데이터(통화 코드 문자열)로 표현하려면
// currency()를 반환하는 메서드가 필요하다.
//
// currency() 메서드가 아직 없으므로 컴파일 오류 → Red 상태
// 아래 주석을 해제하면 컴파일 오류 발생:
//   The method currency() is undefined for the type Money
class MoneyTest {

  @Test
  @SuppressWarnings("java:S2699") // Red 상태 시연용: 어서션 없음이 의도된 것
  void testCurrency() {
    // Money 클래스에 currency()가 없으므로 컴파일 오류 발생 → Red
    // assertEquals("USD", Money.dollar(1).currency());
    // assertEquals("CHF", Money.franc(1).currency());
  }

  @Test
  void testDollarMultiplication() {
    Money five = Money.dollar(5);
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
  }

  @Test
  void testFrancMultiplication() {
    Money five = Money.franc(5);
    assertEquals(Money.franc(10), five.times(2));
    assertEquals(Money.franc(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    assertTrue(Money.franc(5).equals(Money.franc(5)));
    assertFalse(Money.franc(5).equals(Money.franc(6)));
    // currency 기반 비교: amount가 같아도 통화가 다르면 false
    assertFalse(Money.dollar(5).equals(Money.franc(5)));
  }
}
