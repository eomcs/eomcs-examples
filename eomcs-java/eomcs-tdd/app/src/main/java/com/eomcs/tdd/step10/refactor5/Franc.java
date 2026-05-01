package com.eomcs.tdd.step10.refactor5;

class Franc extends Money {

  Franc(int amount, String currency) {
    super(amount, currency);
  }

  // Franc 대신 Money 객체를 생성하도록 변경
  @Override
  Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
  }
}
