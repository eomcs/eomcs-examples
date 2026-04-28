package com.eomcs.tdd.step11.refactor3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// 하위 클래스 제거 후 최종 테스트
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
  void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
  }

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
    // 객체 생성 로직이 통합되면서, 달러와 프랑의 동등성 검증이 동일한 로직으로 검증된다.
    // 따라서, 달러와 프랑의 동등성 검증을 하나로 합치고, 다른 통화 간의 동등성 검증은 유지한다.
    assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    assertFalse(Money.dollar(5).equals(Money.franc(5)));
  }
}
