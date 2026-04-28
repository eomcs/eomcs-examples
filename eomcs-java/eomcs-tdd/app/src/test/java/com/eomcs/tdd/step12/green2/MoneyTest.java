package com.eomcs.tdd.step12.green2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// 원하는 API를 상상하여 테스트 작성
//
// 등장한 새 객체들:
//   Expression  - plus()의 반환 타입 (Sum과 Money가 구현)
//   Sum         - 두 Expression의 합 (augend + addend), 지연 계산
//   Bank        - reduce()로 Expression을 최종 Money로 변환
class MoneyTest {

  @Test
  void testSimpleAddition() {
    // 5단계: 기초 화폐(Money) 객체 생성 및 최종 완성
    Money five = Money.dollar(5);

    // 4단계: 수식(Expression) 메타포 적용하기
    Expression sum = five.plus(five); // 더하기 계산식을 만들어 리턴한다.

    // 3단계: Bank 객체 생성하기
    Bank bank = new Bank();

    // 2단계: 환율을 적용해 결과를 만들어내는 '은행(Bank)' 메타포 적용하기
    Money reduced = bank.reduce(sum, "USD"); // 이 시점에 5 + 5 = 10 계산

    // 1단계: 가장 마지막에 확인할 결과물을 정의하기
    assertEquals(Money.dollar(10), reduced);
  }

  @Test
  void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
  }

  @Test
  void testMultiplication() {
    Money five = Money.dollar(5);
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
  }

  @Test
  void testEquality() {
    assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    assertFalse(Money.dollar(5).equals(Money.franc(5)));
  }
}
