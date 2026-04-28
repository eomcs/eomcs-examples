package com.eomcs.tdd.step08.green;

class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
