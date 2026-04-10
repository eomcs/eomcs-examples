package com.eomcs.tdd.ch09.step03_refactor;

// [step03 - Refactor] times()를 동일하게 만들기
//
abstract class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  static Money franc(int amount) {
    return new Franc(amount);
  }

  abstract Money times(int multiplier);

  String currency() {
    return currency;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Money)) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount && currency.equals(other.currency);
  }
}
