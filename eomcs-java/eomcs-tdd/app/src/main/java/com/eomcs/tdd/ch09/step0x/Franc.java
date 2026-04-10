package com.eomcs.tdd.ch09.step0x;

// [step01] times() 내부에서 new Franc() 대신 팩토리 메서드 사용
//
// Before (ch08):
//   Money times(int multiplier) { return new Franc(amount * multiplier); }
//
// After:
//   Money times(int multiplier) { return Money.franc(amount * multiplier); }
class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  @Override
  Money times(int multiplier) {
    return Money.franc(amount * multiplier); // new Franc() → Money.franc()
  }
}
