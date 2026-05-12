package com.eomcs.tdd.step15.refactor;

class Money implements Expression {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  // Expression 인터페이스에 reduce() 메서드가 추가되었으므로,
  // Money 클래스도 이 메서드를 구현해야 한다.
  @Override
  public Money reduce(String to) {
    return this; // 이미 축약된 Money 객체이므로 자신을 반환한다.
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
