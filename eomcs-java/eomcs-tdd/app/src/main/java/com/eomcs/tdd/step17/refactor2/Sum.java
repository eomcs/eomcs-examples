package com.eomcs.tdd.step17.refactor2;

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

  // Expression 인터페이스에 plus() 메서드가 추가되었기 때문에 구현해야 한다.
  @Override
  public Expression plus(Expression addend) {
    return null;
  }
}
