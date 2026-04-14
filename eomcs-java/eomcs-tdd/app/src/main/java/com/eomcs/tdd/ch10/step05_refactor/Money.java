package com.eomcs.tdd.ch10.step05_refactor;

// [step05_refactor] times()에서 Money객체를 반환하기
//
class Money {

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

  Money times(int multiplier) {
    return null;
  }

  String currency() {
    return currency;
  }

  @Override
  public boolean equals(Object obj) {
    Money other = (Money) obj;
    return amount == other.amount && currency().equals(other.currency());
  }

  // toString() 메서드를 오버라이딩 한다.
  @Override
  public String toString() {
    return amount + " " + currency;
  }
}
