package com.eomcs.tdd.ch08.step01;

// [step01] Dollar.times() 반환 타입을 Dollar → Money 로 변경
//
// Before (ch07):
//   Dollar times(int multiplier) { return new Dollar(amount * multiplier); }
//
// After:
//   Money times(int multiplier) { return new Dollar(amount * multiplier); }
//
// 반환 타입만 바꿨을 뿐 내부 구현은 동일하다.
// 기존 테스트는 변경 없이 그대로 통과한다. (Green 유지)
//
// 이제 Dollar.times()와 Franc.times()의 반환 타입이 모두 Money로 통일되었다.
// → 두 메서드를 Money로 올릴 수 있는 조건이 갖춰졌다. (step02 이후)
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Money times(int multiplier) {          // Dollar → Money 로 변경
    return new Dollar(amount * multiplier);
  }
}
