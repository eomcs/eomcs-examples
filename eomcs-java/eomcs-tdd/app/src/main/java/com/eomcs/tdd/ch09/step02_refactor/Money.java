package com.eomcs.tdd.ch09.step02_refactor;

// [step02 - Refactor] currency를 Money로 올리고 times() 통합
//
// 변경 내용:
//   1. currency 필드와 currency() → Money로 이동 (pull up)
//   2. Money 생성자가 amount와 currency를 모두 받음
//
abstract class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  static Money franc(int amount) {
    return new Franc(amount);
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
