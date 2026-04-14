package com.eomcs.tdd.ch12;

// Chapter 12: Addition, Finally - 최종 결과
//
// 두 Expression의 합을 나타내는 지연 계산 객체
// bank.reduce() 호출 시 비로소 실제 계산이 이루어진다.
class Sum implements Expression {

  Expression augend; // 피가수 (앞의 값)
  Expression addend; // 가수   (뒤의 값)

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
