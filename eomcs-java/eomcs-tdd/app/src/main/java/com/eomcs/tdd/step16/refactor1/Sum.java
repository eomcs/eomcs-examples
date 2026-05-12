package com.eomcs.tdd.step16.refactor1;

class Sum implements Expression {
  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }

  // Expression 인터페이스의 reduce() 메서드의 시그너처 변경에 맞춰
  // Sum 클래스의 reduce() 메서드도 시그너처를 변경한다.
  @Override
  public Money reduce(Bank bank, String to) {
    int amount = augend.amount + addend.amount;
    return new Money(amount, to);
  }
}
