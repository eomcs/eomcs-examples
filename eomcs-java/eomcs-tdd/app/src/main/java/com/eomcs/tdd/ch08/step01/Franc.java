package com.eomcs.tdd.ch08.step01;

// [step01] Franc.times() 반환 타입을 Franc → Money 로 변경
//
// Before (ch07):
//   Franc times(int multiplier) { return new Franc(amount * multiplier); }
//
// After:
//   Money times(int multiplier) { return new Franc(amount * multiplier); }
class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  Money times(int multiplier) {          // Franc → Money 로 변경
    return new Franc(amount * multiplier);
  }
}
