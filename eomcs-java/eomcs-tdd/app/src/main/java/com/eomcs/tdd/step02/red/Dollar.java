package com.eomcs.tdd.step02.red;

class Dollar {

  int amount;

  Dollar(int amount) {
    this.amount = amount;
  }

  void times(int multiplier) {
    amount *= multiplier;
  }
}
