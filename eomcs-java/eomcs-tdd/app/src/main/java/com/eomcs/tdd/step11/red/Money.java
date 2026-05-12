package com.eomcs.tdd.step11.red;

class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  // plus() 메서드를 추가하여 컴파일을 통과시킨다.
  Money plus(Money addend) {
    return null;
  }

  static Money dollar(int amount) {
    return new Money(amount, "USD");
  }

  static Money franc(int amount) {
    return new Money(amount, "CHF");
  }

  Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
  }

  String currency() {
    return currency;
  }

  @Override
  public boolean equals(Object obj) {
    Money other = (Money) obj;
    return amount == other.amount && currency().equals(other.currency());
  }
}
