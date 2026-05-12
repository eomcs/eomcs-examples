package com.eomcs.tdd.step14.red;

class Money implements Expression {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
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

  // 테스트 디버깅을 위해 toString() 메서드를 추가한다.
  @Override
  public String toString() {
    return amount + " " + currency;
  }
}
