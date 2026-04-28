package com.eomcs.tdd.step09.refactor;

class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  @Override
  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
