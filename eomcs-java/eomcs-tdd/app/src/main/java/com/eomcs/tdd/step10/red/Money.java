package com.eomcs.tdd.step10.red;

abstract class Money {

  protected int amount;

  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  static Money franc(int amount) {
    return new Franc(amount);
  }

  abstract Money times(int multiplier);

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
