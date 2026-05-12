package com.eomcs.tdd.step15.refactor;

class Sum implements Expression {
  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }

  // Expression 인터페이스의 reduce() 메서드를 구현한다.
  @Override
  public Money reduce(String to) {
    // 이 메서드에서 augend와 addend의 금액을 실제로 더하여 새로운 Money 객체를 반환한다.
    int amount = augend.amount + addend.amount;
    return new Money(amount, to);
  }
}
