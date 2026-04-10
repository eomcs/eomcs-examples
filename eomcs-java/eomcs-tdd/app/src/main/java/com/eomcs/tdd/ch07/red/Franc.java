package com.eomcs.tdd.ch07.red;

// [Red] Franc - ch06 refactor03 상태 그대로
class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
