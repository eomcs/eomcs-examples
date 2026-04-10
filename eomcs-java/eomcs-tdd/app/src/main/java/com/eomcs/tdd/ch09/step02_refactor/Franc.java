package com.eomcs.tdd.ch09.step02_refactor;

// [step02 - Refactor] Franc - currency 필드, currency()를 Money로 이동.
class Franc extends Money {

  Franc(int amount) {
    super(amount, "CHF");
  }

  @Override
  Money times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
