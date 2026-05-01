package com.eomcs.tdd.step10.refactor2;

class Franc extends Money {
  // - currency 필드, currency()를 Money로 이동.

  Franc(int amount) {
    this.amount = amount;
    this.currency = "CHF";
  }

  @Override
  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
