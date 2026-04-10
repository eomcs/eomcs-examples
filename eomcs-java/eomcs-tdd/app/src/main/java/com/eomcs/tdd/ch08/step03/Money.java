package com.eomcs.tdd.ch08.step03;

// [step03] Money에 times() 메서드 추가
abstract class Money { // 추상 클래스로 변경

  protected int amount;

  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  static Money franc(int amount) {
    return new Franc(amount);
  }

  // 하위 클래스에서 구현할 추상 메서드 선언
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
