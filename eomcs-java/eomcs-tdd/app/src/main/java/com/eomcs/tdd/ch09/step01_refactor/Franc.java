package com.eomcs.tdd.ch09.step01_refactor;

// [step01 - Refactor] Franc에 currency 필드 추가
class Franc extends Money {

  private String currency;

  Franc(int amount) {
    this.amount = amount;
    this.currency = "CHF";
  }

  @Override
  String currency() {
    return currency;
  }

  @Override
  Money times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
