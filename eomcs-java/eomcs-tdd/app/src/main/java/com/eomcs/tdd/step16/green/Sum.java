package com.eomcs.tdd.step16.green;

class Sum implements Expression {
  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }

  @Override
  public Money reduce(String to) {
    int amount = augend.amount + addend.amount;
    return new Money(amount, to);
  }
}
