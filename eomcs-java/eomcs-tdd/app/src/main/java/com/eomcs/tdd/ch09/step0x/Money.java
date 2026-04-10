package com.eomcs.tdd.ch09.step0x;

// [step01] Money - ch08 step03 상태 그대로
// 이번 단계의 변경 대상은 Dollar/Franc 의 times() 내부 구현이다.
abstract class Money {

  protected int amount;

  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  static Money franc(int amount) {
    return new Franc(amount);
  }

  abstract Money times(int multiplier);

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
