package com.eomcs.tdd.step10.refactor6;

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

  // Dollar/Franc 클래스에서 times() 메서드가 중복되므로, 이 부분을 Money 클래스의 메서드로 pull up하여 중복 제거
  Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
  }

  String currency() {
    return currency;
  }

  @Override
  public boolean equals(Object obj) {
    Money other = (Money) obj;
    return amount == other.amount && currency.equals(other.currency);
  }
}
