package com.eomcs.tdd.step10.refactor2;

//   1. currency 필드와 currency() → Money로 이동 (pull up)
//
abstract class Money {

  protected int amount;

  // 하위 클래스의 공통 필드를 상위 클래스로 이동
  protected String currency;

  // Money 생성자가 amount와 currency를 모두 받음
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

  // 하위 클래스의 공통 메서드를 상위 클래스로 이동
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
