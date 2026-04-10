package com.eomcs.tdd.ch06.refactor01;

// [Refactor 01] Franc - 아직 변경 없음 (ch05.green 상태 그대로)
//
// Dollar가 Money를 상속하도록 바꾸는 동안 Franc은 손대지 않는다.
// TDD의 중요한 원칙: 한 번에 하나씩 바꾼다.
// → 모든 테스트가 통과하는 상태를 유지하면서 조금씩 변경한다.
class Franc {

  private int amount;

  Franc(int amount) {
    this.amount = amount;
  }

  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }

  @Override
  public boolean equals(Object obj) {
    Franc other = (Franc) obj;
    return amount == other.amount;
  }
}
