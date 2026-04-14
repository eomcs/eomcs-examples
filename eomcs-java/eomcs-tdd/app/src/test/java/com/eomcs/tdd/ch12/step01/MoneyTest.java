package com.eomcs.tdd.ch12.step01;

import org.junit.jupiter.api.Test;

// [step01 - Red] $5 + $5 = $10 테스트 작성
//
// 아직 Expression, Bank, Sum, plus() 가 모두 없으므로 컴파일 오류 발생.
// 이것이 Red 상태다.
//
// 테스트 코드를 먼저 작성함으로써 필요한 설계가 드러난다:
//   1. Expression: plus()의 반환 타입 (Sum이나 Money를 모두 담을 수 있어야 함)
//   2. Money.plus(): 두 Money를 더해 Expression 반환
//   3. Bank: 환율을 보유하고 reduce()로 최종 Money 계산
//   4. Bank.reduce(Expression, String): Expression을 목표 통화의 Money로 변환
class MoneyTest {

  @Test
  @SuppressWarnings("java:S2699") // Red 상태 시연용: 어서션이 주석 처리되어 있음
  void testSimpleAddition() {
    // Money five = Money.dollar(5);
    // Expression sum = five.plus(five);  // 컴파일 오류: plus() 없음
    // Bank bank = new Bank();            // 컴파일 오류: Bank 없음
    // Money reduced = bank.reduce(sum, "USD"); // 컴파일 오류: reduce() 없음
    // assertEquals(Money.dollar(10), reduced);
  }
}
