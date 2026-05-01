package com.eomcs.tdd.step10.refactor3;

class Franc extends Money {

  // 통화를 파라미터로 받도록 변경
  Franc(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  @Override
  Franc times(int multiplier) {
    return new Franc(amount * multiplier, currency); // 생성자가 변경되었으므로 currency를 추가로 전달
  }
}
