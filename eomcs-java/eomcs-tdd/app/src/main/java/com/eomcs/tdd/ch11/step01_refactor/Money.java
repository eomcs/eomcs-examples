package com.eomcs.tdd.ch11.step01_refactor;

// [step01_refactor] Dollar 클래스 제거
//
// Dollar는 생성자만 남은 빈 껍데기였다.
// 팩토리 메서드 dollar()에서 new Dollar() 대신 new Money()를 직접 반환하면
// Dollar 클래스가 필요 없어진다.
//
// Before:
//   static Money dollar(int amount) { return new Dollar(amount, "USD"); }
//
// After:
//   static Money dollar(int amount) { return new Money(amount, "USD"); }
//
// Dollar.java 파일을 삭제한다. Franc은 아직 남아 있다.
class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Money dollar(int amount) {
    return new Money(amount, "USD"); // new Dollar() → new Money()
  }

  static Money franc(int amount) {
    return new Franc(amount, "CHF"); // 아직 Franc 사용 중
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

  @Override
  public String toString() {
    return amount + " " + currency;
  }
}
