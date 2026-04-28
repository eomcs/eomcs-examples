package com.eomcs.tdd.step10.red;

class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  @Override
  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
