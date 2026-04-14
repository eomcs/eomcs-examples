package com.eomcs.tdd.ch12.step02;

// [step02 - Green] Sum: 두 Expression의 합을 나타내는 Expression
//
// plus()의 결과를 즉시 계산하지 않고 Sum 객체로 저장해둔다.
// bank.reduce()가 호출될 때 비로소 실제 계산이 이루어진다.
//
// augend(피가수): 더해지는 첫 번째 값 ($5 + $5 에서 앞의 $5)
// addend(가수):   더하는 두 번째 값 ($5 + $5 에서 뒤의 $5)
class Sum implements Expression {

  Expression augend;
  Expression addend;

  Sum(Expression augend, Expression addend) {
    this.augend = augend;
    this.addend = addend;
  }

  // 두 Expression을 각각 reduce한 후 amount를 합산한다.
  // 이번 장에서는 같은 통화끼리만 더하므로 단순히 amount를 합산한다.
  @Override
  public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
    return new Money(amount, to);
  }
}
