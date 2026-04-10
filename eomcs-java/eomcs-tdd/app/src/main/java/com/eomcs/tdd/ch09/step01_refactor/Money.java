package com.eomcs.tdd.ch09.step01_refactor;

// [step01 - Refactor] Dollar/Franc 각자에 currency 필드와 currency() 메서드 추가
//
// Money에는 아직 currency가 없다.
// 먼저 각 하위 클래스에 추가하여 테스트를 통과시킨다.
abstract class Money {

  protected int amount;

  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  static Money franc(int amount) {
    return new Franc(amount);
  }

  abstract Money times(int multiplier);

  abstract String currency();

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
