package com.eomcs.tdd.ch09.step04_refactor;

// [step04 - Refactor] 생성자에 currency 값을 전달하기
//
// 변경 내용:
//   1. 하드 코딩되어 있는 currency 필드 값을 생성자로 전달하도록 변경
class Dollar extends Money {

  Dollar(int amount, String currency) {
    super(amount, currency);
  }

  @Override
  Money times(int multiplier) {
    return Money.dollar(amount * multiplier);
  }
}
