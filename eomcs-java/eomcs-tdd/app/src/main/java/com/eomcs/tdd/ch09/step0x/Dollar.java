package com.eomcs.tdd.ch09.step0x;

// [step01] times() 내부에서 new Dollar() 대신 팩토리 메서드 사용
//
// Before (ch08):
//   Money times(int multiplier) { return new Dollar(amount * multiplier); }
//
// After:
//   Money times(int multiplier) { return Money.dollar(amount * multiplier); }
//
// 이제 Dollar.times()와 Franc.times()를 나란히 놓으면:
//   Money.dollar(amount * multiplier)   ← Dollar
//   Money.franc(amount * multiplier)    ← Franc
//
// 메서드 이름(dollar vs franc)만 다를 뿐 구조가 완전히 동일하다.
// 이 차이를 currency 문자열로 표현하면 하나로 합칠 수 있다. (step02 이후)
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  @Override
  Money times(int multiplier) {
    return Money.dollar(amount * multiplier); // new Dollar() → Money.dollar()
  }
}
