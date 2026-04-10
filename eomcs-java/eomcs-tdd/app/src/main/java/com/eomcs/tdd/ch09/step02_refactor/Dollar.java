package com.eomcs.tdd.ch09.step02_refactor;

// [step02 - Refactor] Dollar - currency 필드, currency()를 Money로 이동.
class Dollar extends Money {

  Dollar(int amount) {
    super(amount, "USD");
  }

  @Override
  Money times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
