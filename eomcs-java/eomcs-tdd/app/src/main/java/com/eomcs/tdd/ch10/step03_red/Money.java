package com.eomcs.tdd.ch10.step03_red;

// [step03_red] 디버깅을 위한 toString() 추가
//
// 변경 내용:
//    - toString() 메서드를 추가하여 객체 상태를 명확히 출력한다.
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
