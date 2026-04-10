package com.eomcs.tdd.ch09.step01_red;

// [step01 - Red] currency()가 없으므로 테스트의 five.currency() 호출이 컴파일 오류
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  @Override
  Money times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
