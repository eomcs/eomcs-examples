package com.eomcs.tdd.step13.refactor;

// Expression 인터페이스의 reduce() 메서드를 구현한다.
class Sum implements Expression {
  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }

  // Expression 인터페이스의 reduce() 메서드 구현으로 변경
  @Override
  public Money reduce(String to) {
    int amount = augend.amount + addend.amount;
    return new Money(amount, to);
  }
}
