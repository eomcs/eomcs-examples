package com.eomcs.tdd.ch03.step02.red;

class Dollar {
  int amount;

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }

  @Override
  public boolean equals(Object obj) {
    return true;
  }
}
