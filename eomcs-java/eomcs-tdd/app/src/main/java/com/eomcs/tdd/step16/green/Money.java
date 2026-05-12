package com.eomcs.tdd.step16.green;

class Money implements Expression {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  @Override
  public Money reduce(String to) {
    // 일단 테스트 통과를 위해 하드 코딩으로 환율 계산을 구현한다.
    int rate = (currency.equals("CHF") && to.equals("USD")) ? 2 : 1;
    return new Money(amount / rate, to);
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
