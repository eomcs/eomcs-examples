package com.eomcs.tdd.ch07.green;

// [Green] Franc - 변경 없음
class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
