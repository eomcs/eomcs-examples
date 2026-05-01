package com.eomcs.tdd.step10.refactor4;

abstract class Money {

  protected int amount;
  protected String currency;

  // 하위 클래스의 생성자에서 중복된 코드를 제거하기 위해 상위 클래스의 생성자로 pull up
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
