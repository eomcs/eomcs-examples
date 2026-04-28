package com.eomcs.tdd.step11.refactor1;

// 하위 클래스 제거 준비
//
// Money 클래스의 팩토리에서 생성자에 currency 값을 전달하기
//
abstract class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Money dollar(int amount) {
    // 여기서 currency 값을 생성자에 전달하도록 변경한다.
    return new Dollar(amount, "USD");
  }

  static Money franc(int amount) {
    // 여기서 currency 값을 생성자에 전달하도록 변경한다.
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
