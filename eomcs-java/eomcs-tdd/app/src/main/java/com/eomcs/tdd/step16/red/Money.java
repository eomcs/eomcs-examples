package com.eomcs.tdd.step16.red;

class Money implements Expression {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  @Override
  public Money reduce(String to) {
    return this;
  }

  Expression plus(Money addend) {
    return new Sum(this, addend);
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

  @Override
  public String toString() {
    return amount + " " + currency;
  }
}
