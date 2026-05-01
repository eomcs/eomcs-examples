package com.eomcs.tdd.step10.refactor3;

abstract class Money {

  protected int amount;
  protected String currency;

  static Money dollar(int amount) {
    // 생성자가 변경되었으므로 currency 값을 전달
    return new Dollar(amount, "USD");
  }

  static Money franc(int amount) {
    // 생성자가 변경되었으므로 currency 값을 전달
    return new Franc(amount, "CHF");
  }

  abstract Money times(int multiplier);

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
}
