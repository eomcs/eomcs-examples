package com.eomcs.tdd.ch11.step03_refactor;

// [step03_refactor] 최종 결과 - Money 하나만 남는다
//
//   Dollar.java → 삭제 (ch11 step01)
//   Franc.java  → 삭제 (ch11 step02)
//   Money.java  → 단일 클래스로 모든 통화 처리
//
class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
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
