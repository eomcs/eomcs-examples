package com.eomcs.tdd.step09.green;

class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  // 이제 times()는 Money의 메서드를 오버라이딩한다.
  @Override
  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
