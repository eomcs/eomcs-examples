package com.eomcs.tdd.ch06.refactor03;

// [Refactor 03] Dollar - 변경 없음 (Refactor 02와 동일)
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
