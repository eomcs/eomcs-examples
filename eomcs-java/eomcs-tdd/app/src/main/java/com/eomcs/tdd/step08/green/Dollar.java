package com.eomcs.tdd.step08.green;

class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
