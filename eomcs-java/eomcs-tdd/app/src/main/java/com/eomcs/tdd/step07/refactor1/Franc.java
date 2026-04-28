package com.eomcs.tdd.step07.refactor1;

class Franc {

  private int amount;

  Franc(int amount) {
    this.amount = amount;
  }

  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }

  @Override
  public boolean equals(Object obj) {
    Franc other = (Franc) obj;
    return amount == other.amount;
  }
}
