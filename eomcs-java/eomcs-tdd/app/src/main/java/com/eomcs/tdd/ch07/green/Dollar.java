package com.eomcs.tdd.ch07.green;

// [Green] Dollar - 변경 없음
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
