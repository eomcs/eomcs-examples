package com.eomcs.tdd.step17.refactor1;

class Money implements Expression {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  @Override
  public Money reduce(Bank bank, String to) {
    int rate = bank.rate(currency, to);
    return new Money(amount / rate, to);
  }

  // 리턴 타입을 Expression으로 변경한다.
  Expression plus(Expression addend) {
    return new Sum(this, addend);
  }

  static Money dollar(int amount) {
    return new Money(amount, "USD");
  }

  static Money franc(int amount) {
    return new Money(amount, "CHF");
  }

  // 리턴 타입을 Expression으로 변경한다.
  Expression times(int multiplier) {
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
