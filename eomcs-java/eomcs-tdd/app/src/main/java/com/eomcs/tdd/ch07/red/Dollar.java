package com.eomcs.tdd.ch07.red;

// [Red] Dollar - ch06 refactor03 상태 그대로
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
