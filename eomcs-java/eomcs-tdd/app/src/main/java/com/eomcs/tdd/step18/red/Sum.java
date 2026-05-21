package com.eomcs.tdd.step18.red;

class Sum implements Expression {

  Expression augend; // 피가수
  Expression addend; // 가수

  Sum(Expression augend, Expression addend) {
    this.augend = augend;
    this.addend = addend;
  }

  @Override
  public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
    return new Money(amount, to);
  }

  @Override
  public Expression plus(Expression addend) {
    return null;
  }
}
