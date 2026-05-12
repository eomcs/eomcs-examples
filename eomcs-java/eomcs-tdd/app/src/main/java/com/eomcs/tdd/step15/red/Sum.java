package com.eomcs.tdd.step15.red;

class Sum implements Expression {
  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }
}
