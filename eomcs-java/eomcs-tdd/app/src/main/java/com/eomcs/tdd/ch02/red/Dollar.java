package com.eomcs.tdd.ch02.red;

class Dollar {

  int amount;

  Dollar(int amount) {
    this.amount = amount;
  }

  void times(int multiplier) {
    amount *= multiplier;
  }
}
