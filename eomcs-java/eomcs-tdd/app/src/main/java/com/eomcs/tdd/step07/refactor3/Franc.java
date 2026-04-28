package com.eomcs.tdd.step07.refactor3;

// [Refactor 03] Franc도 Money를 상속
//
// Before (Refactor 02까지의 상태):
//   class Franc {
//     private int amount;     ← 직접 선언
//     equals() { ... }        ← 직접 구현
//   }
//
// After:
//   class Franc extends Money {
//     (amount는 Money에서 상속)
//     (equals()는 Money에서 상속)
//   }
//
// Dollar/Franc 중복 제거 완료:
// - amount 필드 → Money로 이동
// - equals() → Money로 이동
// - times() → 반환 타입이 각각 Dollar/Franc으로 달라 아직 올릴 수 없음 (다음 챕터)
class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
