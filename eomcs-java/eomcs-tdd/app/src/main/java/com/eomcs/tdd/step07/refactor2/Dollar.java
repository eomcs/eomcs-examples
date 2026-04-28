package com.eomcs.tdd.step07.refactor2;

// Dollar에서 equals() 제거
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
