package com.eomcs.tdd.ch10.step05_refactor;

// [step05_refactor] times()에서 Money객체를 반환하기
class Dollar extends Money {

  Dollar(int amount, String currency) {
    super(amount, currency);
  }

  @Override
  Money times(int multiplier) {
    // equals()에서 타입이 아니라 currency를 비교하도록 변경했으므로 Money 객체를 반환해도 된다.
    return new Money(amount * multiplier, currency);
  }
}
