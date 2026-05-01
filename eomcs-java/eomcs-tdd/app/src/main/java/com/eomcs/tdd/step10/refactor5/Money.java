package com.eomcs.tdd.step10.refactor5;

// Money의 객체를 생성할 수 있도록 concrete 클래스로 변경한다.
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

  // Money가 concrete 클래스로 변경되었으므로 times() 메서드도 구현한다.
  Money times(int multiplier) {
    return null; // 하위 클래스에서 오버라이딩할 것이므로 일단 null을 리턴한다.
  }

  String currency() {
    return currency;
  }

  // 객체의 타입을 비교하는 대신에 통화가 같은지를 비교하도록 변경한다.
  @Override
  public boolean equals(Object obj) {
    Money other = (Money) obj;
    return amount == other.amount && currency.equals(other.currency);
  }
}
