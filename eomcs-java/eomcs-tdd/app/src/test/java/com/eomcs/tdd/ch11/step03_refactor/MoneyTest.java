package com.eomcs.tdd.ch11.step03_refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// [step03_refactor] 중복 테스트 제거 후 최종 테스트
//
// 제거한 테스트:
//   - testFrancMultiplication(): times()가 Money로 통합되어
//     testDollarMultiplication()과 동일한 로직을 검증하는 중복
//
// 남은 테스트:
//   - testMultiplication(): 곱셈 동작 검증 (Dollar/Franc 통합)
//   - testEquality(): 동등성 검증 (같은 currency, 다른 currency)
//   - testCurrency(): 통화 코드 반환 검증
class MoneyTest {

  @Test
  void testMultiplication() {
    // testDollarMultiplication + testFrancMultiplication 통합
    // Dollar와 Franc의 구분이 사라졌으므로 하나로 합친다.
    Money five = Money.dollar(5);
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    assertTrue(Money.franc(5).equals(Money.franc(5)));
    assertFalse(Money.franc(5).equals(Money.franc(6)));
    assertFalse(Money.dollar(5).equals(Money.franc(5)));
  }

  @Test
  void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
  }
}
