package com.eomcs.tdd.step15.red;

class Bank {
  Money reduce(Expression source, String to) {
    Sum sum = (Sum) source;
    int amount = sum.augend.amount + sum.addend.amount;
    return new Money(amount, to);
  }
}
