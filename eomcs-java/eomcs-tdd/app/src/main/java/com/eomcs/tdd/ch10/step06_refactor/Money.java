package com.eomcs.tdd.ch10.step06_refactor;

// [step06_refactor] times()를 Money로 올림 (push up)
//
// 변경 내용:
//   - times() 메서드를 Franc과 Dollar에서 Money로 끌어올린다.
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

  // Dollar.times()와 Franc.times()의 구현이 완전히 동일해졌으므로 이를 Money로 끌어올린다.
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

  // toString() 메서드를 오버라이딩 한다.
  @Override
  public String toString() {
    return amount + " " + currency;
  }
}
