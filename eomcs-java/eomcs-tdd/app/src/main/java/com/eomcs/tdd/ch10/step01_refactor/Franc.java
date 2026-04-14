package com.eomcs.tdd.ch10.step01_refactor;

// [step01_refacgtor] 전진을 위한 일보 후퇴
class Franc extends Money {

  Franc(int amount, String currency) {
    super(amount, currency);
  }

  @Override
  Money times(int multiplier) {
    return new Franc(amount * multiplier, "CHF");
  }
}
