package com.eomcs.tdd.step17.green;

class Sum implements Expression {
  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }

  @Override
  public Money reduce(Bank bank, String to) {
    // 동일 통화로 환산한 후 덧셈을 수행한다.
    int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
    return new Money(amount, to);
  }
}
