package com.eomcs.tdd.ch09.step01_green;

// [step01 - Green] currency() 추상 메서드 추가
abstract class Money {

  protected int amount;

  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  static Money franc(int amount) {
    return new Franc(amount);
  }

  abstract Money times(int multiplier);

  // 하위 클래스에서 통화 단위를 반환하도록 추상 메서드로 선언한다.
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
