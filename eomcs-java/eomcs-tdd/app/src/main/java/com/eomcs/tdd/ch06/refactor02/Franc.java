package com.eomcs.tdd.ch06.refactor02;

// [Refactor 02] Franc - 아직 변경 없음
//
// Dollar의 equals()를 Money로 올렸다.
// 이제 Franc도 같은 작업을 해야 하지만, Refactor 03에서 한다.
// 한 번에 하나씩 바꾸고 테스트로 검증하면서 진행한다.
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
