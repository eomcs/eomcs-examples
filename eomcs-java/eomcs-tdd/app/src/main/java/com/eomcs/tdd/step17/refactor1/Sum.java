package com.eomcs.tdd.step17.refactor1;

class Sum implements Expression {

  // 필드의 타입을 Expression으로 변경한다.
  Expression augend; // 피가수
  Expression addend; // 가수

  // 파라미터 타입도 Expression으로 변경한다.
  Sum(Expression augend, Expression addend) {
    this.augend = augend;
    this.addend = addend;
  }

  @Override
  public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
    return new Money(amount, to);
  }
}
