package com.eomcs.tdd.step15.green;

class Bank {
  Money reduce(Expression source, String to) {
    // Expression이 Money인지 Sum인지 구분하여 처리한다.
    if (source instanceof Money) {
      return (Money) source;
    }

    Sum sum = (Sum) source;
    int amount = sum.augend.amount + sum.addend.amount;
    return new Money(amount, to);
  }
}
