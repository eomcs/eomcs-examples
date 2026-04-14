package com.eomcs.tdd.ch11.step02_refactor;

// [step02_refactor] Franc 클래스도 제거
//
// Before:
//   static Money franc(int amount) { return new Franc(amount, "CHF"); }
//
// After:
//   static Money franc(int amount) { return new Money(amount, "CHF"); }
//
// Franc.java 파일 삭제. Dollar, Franc 모두 사라졌다.
// 상속 구조가 완전히 제거되었다.
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
    return new Money(amount, "CHF"); // new Franc() → new Money()
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
