package com.eomcs.tdd.ch11.step01_refactor;

// [step01_refactor] Franc - 아직 남아있음
// Dollar는 제거했지만 Franc은 step02에서 제거한다.
// 한 번에 하나씩 제거하면서 테스트로 안전을 확인한다.
class Franc extends Money {

  Franc(int amount, String currency) {
    super(amount, currency);
  }
}
