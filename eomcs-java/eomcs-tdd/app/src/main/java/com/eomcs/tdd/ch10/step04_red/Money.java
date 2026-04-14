package com.eomcs.tdd.ch10.step04_red;

// [step04_red] 실험 취소(Back out)와 방어적 코딩
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
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
  }

  // toString() 메서드를 오버라이딩 한다.
  @Override
  public String toString() {
    return amount + " " + currency;
  }
}
