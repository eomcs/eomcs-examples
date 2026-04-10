package com.eomcs.tdd.ch06.refactor01;

// [Refactor 01] Dollar가 Money를 상속, amount 필드 제거
//
// Before (ch05):
//   class Dollar {
//     private int amount;   ← 직접 선언
//     ...
//   }
//
// After:
//   class Dollar extends Money {
//     (amount는 Money에서 상속)
//     ...
//   }
//
// equals()는 아직 Dollar에 그대로 유지한다.
// (Money로 옮기는 것은 [Refactor 02]에서 한다.)
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount; // Money의 protected amount에 대입
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }

  @Override
  public boolean equals(Object obj) {
    Dollar other = (Dollar) obj;
    return amount == other.amount;
  }
}
