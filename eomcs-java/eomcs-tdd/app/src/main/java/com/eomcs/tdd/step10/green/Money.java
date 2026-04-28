package com.eomcs.tdd.step10.green;

abstract class Money {

  protected int amount;

  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  static Money franc(int amount) {
    return new Franc(amount);
  }

  abstract Money times(int multiplier);

  // currency() 추상 메서드 추가
  // - 하위 클래스에서 통화 단위를 반환하도록 재정의 해야 한다.
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
