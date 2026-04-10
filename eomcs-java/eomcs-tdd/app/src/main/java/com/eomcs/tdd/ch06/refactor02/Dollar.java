package com.eomcs.tdd.ch06.refactor02;

// [Refactor 02] Dollar에서 equals() 제거
//
// equals()를 Money로 올렸으므로 Dollar에서 equals()를 삭제한다.
// Dollar는 Money의 equals()를 그대로 상속하여 사용한다.
//
// Before (Refactor 01):
//   @Override
//   public boolean equals(Object obj) { ... }  ← Dollar에 직접 있었음
//
// After:
//   (없음) → Money에서 상속
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
