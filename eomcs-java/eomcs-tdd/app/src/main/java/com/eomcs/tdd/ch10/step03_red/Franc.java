package com.eomcs.tdd.ch10.step03_red;

// [step03_red] 디버깅을 위한 toString() 추가
class Franc extends Money {

  Franc(int amount, String currency) {
    super(amount, currency);
  }

  @Override
  Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
  }
}
