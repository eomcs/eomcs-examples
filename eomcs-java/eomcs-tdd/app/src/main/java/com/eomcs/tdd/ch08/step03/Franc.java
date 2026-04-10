package com.eomcs.tdd.ch08.step03;

// [step03] Money의 times() 추상 메서드를 구현
class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  @Override // 상속 받은 추상 메서드를 구현한다고 표시
  Money times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
