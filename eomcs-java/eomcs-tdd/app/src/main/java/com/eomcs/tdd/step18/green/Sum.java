package com.eomcs.tdd.step18.green;

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

  // Expression 인터페이스에 plus() 메서드를 제대로 동작하도록 구현한다.
  @Override
  public Expression plus(Expression addend) {
    return new Sum(this, addend);
  }
}
