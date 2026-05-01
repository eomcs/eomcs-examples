package com.eomcs.tdd.step10.refactor2;

class Dollar extends Money {
  // - currency 필드, currency()를 Money로 이동.

  Dollar(int amount) {
    this.amount = amount;
    this.currency = "USD";
  }

  @Override
  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
