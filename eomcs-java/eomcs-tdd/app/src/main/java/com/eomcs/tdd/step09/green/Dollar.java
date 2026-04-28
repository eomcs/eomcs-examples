package com.eomcs.tdd.step09.green;

class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  // 이제 times()는 Money의 메서드를 오버라이딩한다.
  @Override
  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
