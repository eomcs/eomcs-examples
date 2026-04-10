package com.eomcs.tdd.ch09.step01_green;

// [step01 - Green] currency() 메서드 추가
class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  @Override
  String currency() {
    return "CHF";
  }

  @Override
  Money times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
