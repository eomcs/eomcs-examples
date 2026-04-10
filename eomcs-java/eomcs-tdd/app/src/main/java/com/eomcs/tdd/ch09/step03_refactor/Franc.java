package com.eomcs.tdd.ch09.step03_refactor;

// [step03 - Refactor] Franc - times()를 동일하게 만들기
//
// 변경 내용:
//   1. times()에서 Money의 팩토리 메서드를 사용하여 객체를 생성하게 만든다.
class Franc extends Money {

  Franc(int amount) {
    super(amount, "CHF");
  }

  @Override
  Money times(int multiplier) {
    return Money.franc(amount * multiplier);
  }
}
