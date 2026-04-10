package com.eomcs.tdd.ch09.step01_green;

// [step01 - Green] currency() 메서드 추가
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  @Override
  String currency() {
    return "USD";
  }

  @Override
  Money times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
