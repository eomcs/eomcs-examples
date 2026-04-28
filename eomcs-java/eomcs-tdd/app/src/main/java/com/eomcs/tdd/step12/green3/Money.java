package com.eomcs.tdd.step12.green3;

// [step02 - Green] Money가 Expression을 구현한다.
//
// bank.reduce(Money.dollar(5), "USD") 처럼 Money를 직접 reduce할 수 있어야 하므로
// Money도 Expression 인터페이스를 구현한다.
//
// reduce(): 같은 통화라면 자기 자신을 그대로 반환한다.
//   (다른 통화 간의 변환은 ch13에서 환율과 함께 다룬다.)
//
// plus(): 두 Money의 합을 Sum(Expression)으로 반환한다.
//   즉시 계산하지 않고 Sum 객체로 표현식을 저장한다.
class Money implements Expression {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Money dollar(int amount) {
    return new Money(amount, "USD");
  }

  static Money franc(int amount) {
    return new Money(amount, "CHF");
  }

  // 덧셈: 결과를 즉시 계산하지 않고 Sum(표현식)으로 반환한다.
  // 이렇게 하면 복잡한 다중 통화 식도 조합할 수 있다.
  Expression plus(Expression addend) {
    return new Sum(this, addend);
  }

  Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
  }

  String currency() {
    return currency;
  }

  // Money 자신은 더 이상 reduce할 것이 없다.
  // 같은 통화라면 자기 자신을 그대로 반환한다.
  // (다른 통화 변환은 ch13에서 환율 추가 후 처리)
  @Override
  public Money reduce(Bank bank, String to) {
    return new Money(amount, to);
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
