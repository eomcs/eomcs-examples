package com.eomcs.tdd.step07.refactor3;

// [Refactor 03] Dollar - 변경 없음
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
