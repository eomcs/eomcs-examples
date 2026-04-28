package com.eomcs.tdd.step11.refactor2;

// 하위 클래스 제거 준비 2
//
// - 팩토리 메서드에서 하위 클래스 대신 Money 객체를 생성하도록 변경한다.
// - Money 클래스를 추상 클래스에서 일반 클래스로 변경한다.
// - times() 메서드에서 Money 객체를 반환하도록 구현한다.
//   하위 클래스의 times() 메서드를 제거한다.
// - equals() 메서드에서 클래스 비교 대신 통화 비교로 변경한다.
class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  // 팩토리 메서드에서 하위 클래스 대신 Money 객체를 생성하도록 변경한다.
  static Money dollar(int amount) {
    return new Money(amount, "USD");
  }

  // 팩토리 메서드에서 하위 클래스 대신 Money 객체를 생성하도록 변경한다.
  static Money franc(int amount) {
    return new Money(amount, "CHF");
  }

  Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
  }

  String currency() {
    return currency;
  }

  @Override
  public boolean equals(Object obj) {
    Money other = (Money) obj;
    return amount == other.amount && currency().equals(other.currency());
  }
}
