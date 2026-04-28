package com.eomcs.tdd.step12.green2;

// Expression 메타포 적용
//
// 1. plus()의 리턴 타입을 Expression으로 변경한다.
// 2. Expression 인터페이스를 구현한다.
//
class Money implements Expression { // Expression 인터페이스 구현

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  // 리턴 타입을 Expression으로 변경한다.
  Expression plus(Money addend) {
    return new Money(amount + addend.amount, currency);
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
