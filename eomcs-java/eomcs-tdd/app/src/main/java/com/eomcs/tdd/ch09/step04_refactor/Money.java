package com.eomcs.tdd.ch09.step04_refactor;

// [step04 - Refactor] 생성자에 currency 값을 전달하기
//
abstract class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Money dollar(int amount) {
    return new Dollar(amount, "USD");
  }

  static Money franc(int amount) {
    return new Franc(amount, "CHF");
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
