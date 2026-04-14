package com.eomcs.tdd.ch10.step04_green;

// [step04_green] 실험 취소(Back out)와 방어적 코딩
//
// 변경 내용:
//   - equals() 메서드에서 클래스 타입 대신 currency를 비교하도록 변경한다.
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
    // 클래스 타입 대신 currency를 비교
    return amount == other.amount && currency().equals(other.currency());
  }

  @Override
  public String toString() {
    return amount + " " + currency;
  }
}
